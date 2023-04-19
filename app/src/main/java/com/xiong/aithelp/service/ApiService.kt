package com.xiong.aithelp.service

import com.google.gson.annotations.SerializedName
import com.xiong.aithelp.dao.*
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("api/users/login")
    fun login(@Body requestBody: RequestBody): Call<UserDao>

    @POST("api/users/register")
    fun register(@Body requestBody: RequestBody): Call<UserDao>

    @GET("api/helps/helpList")
    fun helpList(): Call<List<HelpDao>>

    @POST("api/helps/helpNew")
    fun newHelp(@Body requestBody: RequestBody): Call<OriginalHelpDao>

    @POST("api/helps/helpInfo")
    fun detailHelp(@Body requestBody: RequestBody): Call<HelpDao>

    @POST("api/comments/commentGet")
    fun commentList(@Body requestBody: RequestBody): Call<List<CommentDao>>

    @POST("api/comments/commentNew")
    fun newComment(@Body requestBody: RequestBody): Call<CommentNewResponseDao>

    @POST("api/users/userInfo")
    fun getUserInfo(@Body requestBody: RequestBody): Call<UserDao>

//    @POST("api/users/logout")
//    fun logout(@Body requestBody: RequestBody): Call<LogoutRespone>
}