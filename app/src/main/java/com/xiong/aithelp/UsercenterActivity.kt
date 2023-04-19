package com.xiong.aithelp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xiong.aithelp.dao.CommentNewResponseDao
import com.xiong.aithelp.dao.HelpDao
import com.xiong.aithelp.dao.UserDao
import com.xiong.aithelp.model.ApiModel
import com.xiong.aithelp.model.UserModel
import com.xiong.aithelp.service.ApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.system.exitProcess

class UsercenterActivity : AppCompatActivity() {
    private lateinit var user: UserModel
    private lateinit var api: ApiModel
    private lateinit var viewComp: ViewComp
    private var id: String = ""

    class ViewComp{
        public lateinit var username: TextView
        public lateinit var email: TextView
        public lateinit var telephone: TextView
        public lateinit var score: TextView

        public lateinit var logoutBt: Button
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usercenter)
        userInit()
        componentsInit()
        getInfo()
    }

    fun userInit(){
        this.api = ApiModel()
        val sharedPref = this.getSharedPreferences(this.api.dataFile, MODE_PRIVATE)
        val userModel = UserModel(sharedPref)
        this.user = userModel
    }

    fun componentsInit(){
        this.viewComp = ViewComp()
        this.viewComp.username = findViewById<TextView>(R.id.uc_username_text)
        this.viewComp.email = findViewById<TextView>(R.id.uc_email_text)
        this.viewComp.telephone = findViewById<TextView>(R.id.uc_mobile_text)
        this.viewComp.score = findViewById<TextView>(R.id.uc_score_text)

        this.viewComp.logoutBt = findViewById(R.id.button_logout)
    }

    fun getInfo(){
        val apiModel = this.api
        val self = this
        val retrofit = Retrofit.Builder()
            .baseUrl(apiModel.address)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(ApiService::class.java)

        val jsonObject = JSONObject().apply {
            put("token", self.user.token)
        }
        val requestBody = jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())
        apiService.getUserInfo(requestBody).enqueue(object : Callback<UserDao> {
            override fun onResponse(call: Call<UserDao>, response: Response<UserDao>) {
                val userInfo: UserDao? = response.body()
                if (response.code() == 200 && userInfo != null) {
                    Log.d("http_user_info", userInfo.userName)
                    self.loadDone(userInfo)
                }else{
                    //没有get到
                    Log.d("http_user_empty", response.message())
                }
            }

            override fun onFailure(call: Call<UserDao>, t: Throwable) {
                Log.d("http_user_failed", t.message.toString())
            }
        })
    }

    fun loadDone(userInfo:UserDao){
        this.viewComp.username.text = userInfo.userName
        this.viewComp.email.text = userInfo.email
        this.viewComp.telephone.text = userInfo.telephone
        this.viewComp.score.text = userInfo.score.toString()

        this.viewComp.logoutBt.setOnClickListener {
            this.logout()

        }
    }


    fun logout(){
        this.user.empty()
        this.user.update()
//        val intent = Intent(applicationContext, MainActivity::class.java)
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
//        startActivity(intent)
//        exitProcess(0)
        finish()
        val login = Intent(this,LoginActivity::class.java)
        startActivity(login)
    }

}