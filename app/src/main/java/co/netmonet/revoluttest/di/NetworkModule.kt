package co.netmonet.revoluttest.di

import co.netmonet.revoluttest.BuildConfig
import co.netmonet.revoluttest.data.RevolutService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val networkModule = module {
    single { createOkHttpClient() }
    single { createRetrofit(get()) }
    single { createApi(get()) }
}

fun createOkHttpClient(): OkHttpClient {
    val okHttpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
    val clientBuilder = OkHttpClient.Builder()
    if (BuildConfig.DEBUG) {
        okHttpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        clientBuilder.addInterceptor(okHttpLoggingInterceptor)
    }
    return clientBuilder
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()
}

fun createRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(Properties.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

fun createApi(retrofit: Retrofit) = retrofit.create(RevolutService::class.java)

object Properties {
    const val BASE_URL = "https://revolut.duckdns.org/"

    const val CACHE_SIZE = 10L * 1024L * 1024L
}