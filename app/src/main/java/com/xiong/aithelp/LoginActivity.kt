package com.xiong.aithelp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.widget.Toast
import com.google.gson.annotations.SerializedName
import com.xiong.aithelp.dao.UserDao
import com.xiong.aithelp.model.ApiModel
import com.xiong.aithelp.model.UserModel
import com.xiong.aithelp.service.ApiService
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var user:UserModel
    private lateinit var api:ApiModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportFragmentManager.beginTransaction()
            .add(R.id.login_page_frame,LoginFragment())
            .commit()

//        supportFragmentManager.beginTransaction()
//            .add(R.id.login_page_frame,SignupFragment())
//            .commit()
//
//        supportFragmentManager.beginTransaction()
//            .hide(SignupFragment())
//            .commit()

//        val loginPage = supportFragmentManager.findFragmentById(R.id.login_layout)
        //必须初始化
        userInit()
    }

    fun userInit(){
        this.api = ApiModel()
        val sharedPref = this.getSharedPreferences(this.api.dataFile, MODE_PRIVATE)
        val userModel = UserModel(sharedPref)
        this.user = userModel
    }

    fun login(email:String,password:String,fragment: LoginFragment){
        val apiModel = this.api
        val userModel = this.user
        var self = this
        val retrofit = Retrofit.Builder()
            .baseUrl(apiModel.address)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(ApiService::class.java)
//        val username = "elliot@ait.asia"
//        val password = "123456"
        val jsonObject = JSONObject().apply {
            put("email", email)
            put("password", password)
        }

        val requestBody = jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())
        apiService.login(requestBody).enqueue(object : Callback<UserDao>{
            override fun onResponse(call: Call<UserDao>, response: Response<UserDao>) {
                val user: UserDao? = response.body()
//                d("http_body",response.body().toString())
//                d("http_body2",response.message().toString())
                print(user?.email)
                if (response.code() == 200 && user != null) {
                    userModel.copyFromDao(user)
                    userModel.update()
                    d("http",userModel.token)
                    fragment.haveDone(true)
                }else{
                    //密码错误
                    d("http",response.body().toString())
                    fragment.haveDone(false)
                }
            }

            override fun onFailure(call: Call<UserDao>, t: Throwable) {
                d("http",t.message.toString())
                fragment.haveDone(false)
            }

        })
    }
    //register
    fun register(email:String,password:String,username: String,tel:String,fragment: SignupFragment){
        val apiModel = this.api
        val userModel = this.user
        var self = this
        val retrofit = Retrofit.Builder()
            .baseUrl(apiModel.address)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(ApiService::class.java)
//        val username = "elliot@ait.asia"
//        val password = "123456"
        val jsonObject = JSONObject().apply {
            put("email", email)
            put("password", password)
            put("username", username)
            put("telephone", tel)
        }

        val requestBody = jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())
        apiService.register(requestBody).enqueue(object : Callback<UserDao>{
            override fun onResponse(call: Call<UserDao>, response: Response<UserDao>) {
                val user: UserDao? = response.body()
//                d("http_body",response.body().toString())
//                d("http_body2",response.message().toString())
                print(user?.email)
                if (response.code() == 200 && user != null) {
                    userModel.copyFromDao(user)
                    userModel.update()
                    d("http",userModel.token)
                    fragment.haveDone(true)
                }else{
                    //密码错误
                    d("http",response.body().toString())
                    fragment.haveDone(false)
                }
            }

            override fun onFailure(call: Call<UserDao>, t: Throwable) {
                d("http",t.message.toString())
                fragment.haveDone(false)
            }

        })
    }


    fun toast(text:String){
        val t1:Toast = Toast.makeText(this,text,Toast.LENGTH_LONG)
        t1.show()
    }
}