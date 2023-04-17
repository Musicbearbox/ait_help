package com.xiong.aithelp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xiong.aithelp.dao.HelpDao
import com.xiong.aithelp.dao.OriginalHelpDao
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

class NewActivity : AppCompatActivity() {
    private lateinit var user: UserModel
    private lateinit var api: ApiModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new)
        userInit()

        val bt = findViewById<Button>(R.id.buttonAddHelp)

        val titleVw = findViewById<EditText>(R.id.title_edittext)
        val tagVw = findViewById<EditText>(R.id.tag_edittext)
        val desVw = findViewById<EditText>(R.id.description_edittext)
        val rewardVw = findViewById<EditText>(R.id.reward_edittext)

        bt.setOnClickListener {
            val title = titleVw.text.toString()
            val tag = tagVw.text.toString()
            val des = desVw.text.toString()
            val reward = rewardVw.text.toString()
            if(title==""||tag==""||des==""||reward==""){

            }else{
                this.addHelp(title,tag,des,reward)
            }
        }
    }

    fun userInit(){
        this.api = ApiModel()
        val sharedPref = this.getSharedPreferences(this.api.dataFile, MODE_PRIVATE)
        val userModel = UserModel(sharedPref)
        this.user = userModel
    }

    fun addHelp(title:String,tag:String,des:String,reward:String){
        val apiModel = this.api
        val userModel = this.user
        val self = this
        val retrofit = Retrofit.Builder()
            .baseUrl(apiModel.address)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(ApiService::class.java)
        val jsonObject = JSONObject().apply {
            put("title", title)
            put("tags", arrayOf(tag))
            put("description", des)
            put("reward", reward)
            put("token", self.user.token)
        }
        val requestBody = jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())
        apiService.newHelp(requestBody).enqueue(object : Callback<OriginalHelpDao> {
            override fun onResponse(call: Call<OriginalHelpDao>, response: Response<OriginalHelpDao>) {
                val help: OriginalHelpDao? = response.body()
                if (response.code() == 200 && help != null) {
                    Log.d("http_help_new", help.message)
//                    self.loadDone(helpList)
                }else{
                    //没有get到
                    Log.d("http_help_new", response.body().toString())
                }
            }

            override fun onFailure(call: Call<OriginalHelpDao>, t: Throwable) {
                Log.d("http_help_new", t.message.toString())
            }
        })
    }

    fun loadDone(){

    }
}