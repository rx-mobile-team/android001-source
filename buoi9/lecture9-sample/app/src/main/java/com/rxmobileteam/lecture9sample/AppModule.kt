package com.rxmobileteam.lecture9sample

import com.rxmobileteam.lecture9sample.data.remote.AuthorizationInterceptor
import com.rxmobileteam.lecture9sample.data.remote.UnsplashApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.intellij.lang.annotations.PrintFormat
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UnsplashBaseUrlQualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UnsplashClientIdQualifier

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
  private const val UNSPLASH_BASE_URL = "https://api.unsplash.com/"

  @Provides
  @UnsplashBaseUrlQualifier
  fun provideBaseUrl(): String = UNSPLASH_BASE_URL

  @Provides
  @UnsplashClientIdQualifier
  fun provideClientId(): String = "EbwKAE7hfLK0BeLM0-_pF-nEBy6s7B8u19HUzQSZUNU"

  @Provides
  @Singleton
  fun moshi(): Moshi =
    Moshi.Builder()
      .addLast(KotlinJsonAdapterFactory())
      .build()

  @Provides
  fun httpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
    level = if (BuildConfig.DEBUG) {
      HttpLoggingInterceptor.Level.BODY
    } else {
      HttpLoggingInterceptor.Level.NONE
    }
  }

  @Provides
  @Singleton
  fun okHttpClient(
    authorizationInterceptor: AuthorizationInterceptor,
    httpLoggingInterceptor: HttpLoggingInterceptor,
  ): OkHttpClient {
    return OkHttpClient.Builder()
      .connectTimeout(30, TimeUnit.SECONDS)
      .readTimeout(30, TimeUnit.SECONDS)
      .writeTimeout(30, TimeUnit.SECONDS)
      .addNetworkInterceptor(httpLoggingInterceptor)
      .addInterceptor(authorizationInterceptor)
      .build()
  }

  @Provides
  @Singleton
  fun retrofit(
    okHttpClient: OkHttpClient,
    moshi: Moshi,
    @UnsplashBaseUrlQualifier baseUrl: String,
  ): Retrofit {
    return Retrofit.Builder()
      .baseUrl(baseUrl)
      .client(okHttpClient)
      .addConverterFactory(MoshiConverterFactory.create(moshi))
      .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
      .build()
  }

  @Provides
  @Singleton
  fun unsplashApiService(
    retrofit: Retrofit,
  ): UnsplashApiService = UnsplashApiService(retrofit)
}