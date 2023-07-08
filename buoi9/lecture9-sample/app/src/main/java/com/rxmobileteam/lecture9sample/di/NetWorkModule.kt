package com.rxmobileteam.lecture9sample.di

import com.rxmobileteam.lecture9sample.BuildConfig
import com.rxmobileteam.lecture9sample.data.remote.AuthorizationInterceptor
import com.rxmobileteam.lecture9sample.data.remote.UnsplashApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

val networkModule = module {
  single { providerOkHttpClient() }
  single { createMoshiFactory() }
  factory { createServiceRetrofit<UnsplashApiService>(get(), get()) }
}


const val UNSPLASH_BASE_URL = "https://api.unsplash.com/"

private val moshi = Moshi.Builder()
  .addLast(KotlinJsonAdapterFactory())
  .build()

private fun createMoshiFactory(): MoshiConverterFactory {
  return MoshiConverterFactory.create(moshi)
}

private val httpLoggingInterceptor
  get() = HttpLoggingInterceptor().apply {
    level = if (BuildConfig.DEBUG) {
      HttpLoggingInterceptor.Level.BODY
    } else {
      HttpLoggingInterceptor.Level.NONE
    }
  }

private val authorizationInterceptor
  get() = AuthorizationInterceptor("EbwKAE7hfLK0BeLM0-_pF-nEBy6s7B8u19HUzQSZUNU")

fun providerOkHttpClient(): OkHttpClient {
  return OkHttpClient.Builder()
    .connectTimeout(30, TimeUnit.SECONDS)
    .readTimeout(30, TimeUnit.SECONDS)
    .writeTimeout(30, TimeUnit.SECONDS)
    .addNetworkInterceptor(httpLoggingInterceptor)
    .addInterceptor(authorizationInterceptor)
    .build()
}

private inline fun <reified T> createServiceRetrofit(
  okHttpClient: OkHttpClient,
  createMoshiFactory: MoshiConverterFactory,
): T {
  val retrofit = Retrofit.Builder()
    .baseUrl(UNSPLASH_BASE_URL)
    .client(okHttpClient)
    .addConverterFactory(createMoshiFactory)
    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
    .build()

  return retrofit.create(T::class.java)
}