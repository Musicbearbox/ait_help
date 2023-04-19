package com.xiong.aithelp.dao

import com.google.gson.annotations.SerializedName

class CommentDao{
    class innerUser{
        @SerializedName("_id") var id:String = ""
        @SerializedName("username") var username:String = ""
        @SerializedName("email") var email:String = ""
        @SerializedName("score") var score:Int = 0
        @SerializedName("photo") var photo:String = ""
    }
    @SerializedName("_id") var id: String = ""  //id
    @SerializedName("userId") var userId: String = ""  //username
    @SerializedName("postId")  var postId: String = ""
    @SerializedName("content") var content: String = ""
    @SerializedName("createdAt") var createdAt: String = ""
    @SerializedName("user") var user: Array<innerUser> = arrayOf()

}
