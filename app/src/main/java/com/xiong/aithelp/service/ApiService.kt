package com.xiong.aithelp.service

import com.google.gson.annotations.SerializedName
import com.xiong.aithelp.dao.UserDao
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("api/users/login")
    fun login(@Body requestBody: RequestBody): Call<UserDao>

    @POST("api/users/register")
    fun register(@Body requestBody: RequestBody): Call<UserDao>
}