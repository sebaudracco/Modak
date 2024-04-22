package com.sebadracco.modak.core.base

import com.contentful.vault.SyncConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit




val apiTestModule = module {
    single { providerHttpLoggingInterceptor() }
    single { providerContentfulClient() }
    single { ApiInterceptor() }
}


//Commons

fun providerRetrofit(url: String, client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(url)
        .client(client)
        .build()
}

fun generateCustomClient(timeout: Long): OkHttpClient.Builder {
    return OkHttpClient.Builder()
        .addInterceptor(BodyInterceptor())
        .connectTimeout(timeout, TimeUnit.SECONDS)
        .writeTimeout(timeout, TimeUnit.SECONDS)
        .readTimeout(timeout, TimeUnit.SECONDS)
}



fun providerHttpLoggingInterceptor(): HttpLoggingInterceptor {
    val logging = HttpLoggingInterceptor()
    logging.level =
        HttpLoggingInterceptor.Level.BODY
    return logging
}

fun providerContentfulClient(): SyncConfig {
    return SyncConfig
        .builder()
        .setSpaceId("BuildConfig.CI_CONTENTFUL_SPACE")
        .setAccessToken("BuildConfig.CI_CONTENTFUL_TOKEN")
        .setInvalidate(false)
        .setEnvironment("BuildConfig.CI_CONTENTFUL_ENV")
        .build()
}

class ApiInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val trace = PerformanceManager.startTrace(request.url.encodedPath)
        trace.start()
        try {
            val response = chain.proceed(request)
            trace.stop()
            return response
        } catch (e: Exception) {
            throw  e
        }
    }
}

class BodyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        if (response.code == 204 || response.code == 205) {
            return response
                .newBuilder()
                .code(200)
                .body(response.body)
                .build()
        } else {
            return response
        }
    }
}