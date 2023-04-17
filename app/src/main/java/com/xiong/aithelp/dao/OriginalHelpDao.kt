package com.xiong.aithelp.dao

import com.google.gson.annotations.SerializedName

class OriginalHelpDao{
    @SerializedName("id") var id: String = ""  //id
    @SerializedName("title") var title: String = ""  //username
    @SerializedName("tags")  var tags: List<String> = listOf()
    @SerializedName("userId") var userId: String = ""
    @SerializedName("description") var description: String = ""
    @SerializedName("images") var images: List<String> = listOf()
    @SerializedName("reward") var reward: String = ""
    @SerializedName("taker") var taker: String = ""
    @SerializedName("isSolved") var isSolved: Boolean = false
    @SerializedName("message") var message: String = ""
}
