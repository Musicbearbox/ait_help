package com.xiong.aithelp.model

import android.content.SharedPreferences
import android.util.Log.d
import com.xiong.aithelp.dao.UserDao


class UserModel {
    private lateinit var sharedPref: SharedPreferences
    var userId: String = ""
    var userName: String = ""
    var email: String = ""
    var telephone: String = ""
    var score: Int = 0
    var photo: String = ""
    var isAdmin: Boolean = false
    var token: String = ""
    var expireTime: String = ""

    constructor(sharedPref:SharedPreferences){
        this.sharedPref = sharedPref
        this.userId = this.sharedPref.getString("userId","").toString()
        this.userName = this.sharedPref.getString("userName","").toString()
        this.email = this.sharedPref.getString("email","").toString()
        this.telephone = this.sharedPref.getString("telephone","").toString()
        this.score = this.sharedPref.getInt("score",0)
        this.photo = this.sharedPref.getString("photo","").toString()
        this.isAdmin = this.sharedPref.getBoolean("isAdmin",false)
        this.token = this.sharedPref.getString("token","").toString()
    }

    fun update(){
        val editor = this.sharedPref.edit()
        editor.putString("userId",this.userId)
        editor.putString("userName",this.userName)
        editor.putString("email",this.email)
        editor.putString("telephone",this.telephone)
        editor.putInt("score",this.score)
        editor.putString("photo",this.photo)
        editor.putBoolean("isAdmin",this.isAdmin)
        editor.putString("token",this.token)
        editor.apply()
    }

    fun empty(){
        this.userId = ""
        this.userName = ""
        this.email = ""
        this.telephone = ""
        this.score = 0
        this.photo = ""
        this.isAdmin = false
        this.token = ""
        d("empty","empty")
    }

    fun isLogin(): Boolean {
        return this.token != ""
    }

    fun copyFromDao(userDao:UserDao){
        this.userId = userDao.userId
        this.userName = userDao.userName
        this.email = userDao.email
        this.telephone = userDao.telephone
        this.score = userDao.score
        this.photo = userDao.photo
        this.isAdmin = userDao.isAdmin
        this.token = userDao.token
    }

    fun isAvailable(): Boolean {
        if(this.token=="")
            return false
        else
            return true
    }
}