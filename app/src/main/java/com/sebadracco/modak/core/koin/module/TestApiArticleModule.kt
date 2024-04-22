package com.sebadracco.modak.core.koin.module


import com.sebadracco.modak.core.base.ApiInterceptor
import com.sebadracco.modak.core.base.generateCustomClient
import com.sebadracco.modak.core.base.providerRetrofit
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module



const val TIMEOUT_TRANSACTIONAL   = 150L
const val RETROFIT_API = "RetrofitApi"
const val HTTP_CLIENT = "OkHttpClient"
const val HEADER_CONTENT_TYPE = "Content-Type"
const val CONTENT_TYPE      = "application/json"
const val AIC_HEADER= "AIC-User-Agent"
const val API_BASE_URL= "https://api.artic.edu/api/v1/"
/*
* You may access our API without authentication. Anonymous users are throttled to 60 requests per minute. (Each IP counts as a separate user.) If you are working on an application that needs to exceed this restriction, please get in touch with us at engineering@artic.edu.
* */
const val AIC = "AIC-User-Agent: aic-bash (sebaudracco@gmail.com)"
/*
* Lastly, consider adding a AIC-User-Agent header with the name of your project and a contact email to your API requests
* */


val testApiArticleModule = module {
    single(named(HTTP_CLIENT)) { providerHttpClient(get(), get()) }
    single(named(RETROFIT_API)) {
        providerRetrofit(
            url = API_BASE_URL,
            client = get(named(HTTP_CLIENT))
        )
    }
}

fun providerHttpClient(
    httpLoggingInterceptor: HttpLoggingInterceptor,
    apiInterceptor: ApiInterceptor,
): OkHttpClient {
    val httpClientBuilder = generateCustomClient(TIMEOUT_TRANSACTIONAL)
    httpClientBuilder.addInterceptor { chain: Interceptor.Chain ->
        val builder = generateBasicRequest(chain)
        builder.addHeader(HEADER_CONTENT_TYPE, CONTENT_TYPE)
        //builder.addHeader(AUTH, ARTICLE_API_KEY)   Art Institute of Chicago API   not needed
        builder.addHeader(AIC_HEADER, AIC) //
        chain.proceed(builder.build())
    }
    httpClientBuilder.addInterceptor(httpLoggingInterceptor)
    httpClientBuilder.addInterceptor(apiInterceptor)
    return httpClientBuilder.build()
}
public fun generateBasicRequest(
    chain: Interceptor.Chain
): Request.Builder {
    return chain.request().newBuilder()

}