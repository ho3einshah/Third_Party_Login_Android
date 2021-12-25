package com.example.googlesignin.repo

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Repository {

    fun createOkhttp( accessToken:String):OkHttpClient{
        val interceptor= TokenInterceptor(accessToken)
       val client =  OkHttpClient.Builder()
            .addInterceptor(interceptor).build();
        return client
    }
    fun createRetrofit(baseUrl: String, accessToken: String): Retrofit {
        return Retrofit.Builder() .baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create())
           .client(createOkhttp(accessToken))
            .build()
    }

}