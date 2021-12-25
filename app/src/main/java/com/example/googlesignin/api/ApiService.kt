package com.example.googlesignin.api

import com.example.googlesignin.dto.GitHubLoginModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("access_token")
    fun getAccessToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("code") code: String
    ): Call<ResponseBody>
@GET("user")
fun getParameter():Call<GitHubLoginModel>

}