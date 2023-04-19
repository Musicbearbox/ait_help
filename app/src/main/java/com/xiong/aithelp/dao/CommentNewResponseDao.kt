package com.xiong.aithelp.dao

import com.google.gson.annotations.SerializedName

class CommentNewResponseDao{
    @SerializedName("id") var id: String = ""  //id
    @SerializedName("userId") var userId: String = ""  //username
    @SerializedName("postId")  var postId: String = ""
    @SerializedName("content") var content: String = ""
    @SerializedName("message") var createdAt: String = ""
}
