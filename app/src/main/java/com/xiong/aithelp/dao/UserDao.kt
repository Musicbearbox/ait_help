package com.xiong.aithelp.dao

import com.google.gson.annotations.SerializedName

class UserDao{
    @SerializedName("id") var userId: String = ""  //id
    @SerializedName("username") var userName: String = ""  //username
    @SerializedName("email")  var email: String = ""
    @SerializedName("telephone") var telephone: String = ""
    @SerializedName("score") var score: Int = 0
    @SerializedName("photo") var photo: String = ""
    @SerializedName("isAdmin") var isAdmin: Boolean = false
    @SerializedName("token") var token: String = ""
}
