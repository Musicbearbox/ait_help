package com.xiong.aithelp.model


class ApiModel() {
    var dataFile:String = "user_preferences"

    val address:String = "http://172.21.55.44:3000/"
    val login:String = "api/users/login"
    val signup:String = "api/users/register"
    val logout:String = "api/users/logout"
    val ver:String = "version"
    val userList:String = "api/users/userList"
    val userInfo:String = "api/users/userInfo"
    val helpList:String = "api/helps/helpList"
    val helpNew:String = "api/helps/helpNew"
    val helpInfo:String = "api/helps/helpInfo"
    val commentGet:String = "api/comments/commentGet"
    val commentNew:String = "api/comments/commentNew"
    val userUpdate:String = "api/users/userUpdate"
    val userBan:String = "api/users/userBan"
    val ownHelpList:String = "api/helps/ownHelpList"
    val redisSet:String = "redisSet"
    val redisGet:String = "redisGet"

}