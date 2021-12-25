package com.example.googlesignin.repo

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class TokenInterceptor(private val accessToken : String):Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        //rewrite the request to add bearer token
       val newRequest=chain.request().newBuilder()
            .header("Authorization", "bearer $accessToken")
            .build()
        return chain.proceed(newRequest)
    }
    }
