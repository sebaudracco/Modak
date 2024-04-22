package com.sebadracco.modak.core.koin.module

import AppPreferencesRepository
import com.sebadracco.modak.core.base.generateCustomClient
import com.sebadracco.modak.core.base.providerRetrofit
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module


const val RETROFIT_API_AUTH = "RetrofitAuthApi"
const val HTTP_CLIENT_AUTH = "OkHttpClientAuth"

val apiAuthModule = module {
    //Auth Api
    single(named(HTTP_CLIENT_AUTH)) { providerHttpClientAuth(get(), get()) }

    single(named(RETROFIT_API_AUTH)) {
        providerRetrofit(
            url = API_BASE_URL,
            client = get(named(HTTP_CLIENT)))

    }

}

fun providerHttpClientAuth(
    httpLoggingInterceptor: HttpLoggingInterceptor?,
    appPreferencesRepository: AppPreferencesRepository
): OkHttpClient {
    val httpClientBuilder = generateCustomClient(TIMEOUT_TRANSACTIONAL)
    httpClientBuilder.addInterceptor { chain: Interceptor.Chain ->
        val builder = generateBasicRequest(chain)
        builder.addHeader(HEADER_CONTENT_TYPE, "application/json;charset=utf-8")
        chain.proceed(builder.build())
    }
    if (httpLoggingInterceptor != null) {
        httpClientBuilder.addInterceptor(httpLoggingInterceptor)
    }

    return httpClientBuilder.build()
}

