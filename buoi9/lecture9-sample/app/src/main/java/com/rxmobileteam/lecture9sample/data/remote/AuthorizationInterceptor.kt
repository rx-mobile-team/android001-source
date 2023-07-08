package com.rxmobileteam.lecture9sample.data.remote

import com.rxmobileteam.lecture9sample.UnsplashClientIdQualifier
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthorizationInterceptor @Inject constructor(
  @UnsplashClientIdQualifier private val clientId: String
) : Interceptor {
  override fun intercept(chain: Interceptor.Chain): Response = chain
    .request()
    .newBuilder()
    .addHeader("Authorization", "Client-ID $clientId")
    .build()
    .let(chain::proceed)
}