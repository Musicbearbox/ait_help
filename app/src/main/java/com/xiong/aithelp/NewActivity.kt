package com.xiong.aithelp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xiong.aithelp.dao.HelpDao
import com.xiong.aithelp.dao.OriginalHelpDao
import com.xiong.aithelp.model.ApiModel
import com.xiong.aithelp.model.UserModel
import com.xiong.aithelp.service.ApiService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewActivity : AppCompatActivity() {
    private lateinit var user: UserModel
    private lateinit var api: ApiModel
    private lateinit var viewComp: ViewComp
    class ViewComp{
        public lateinit var title: EditText
        public lateinit var tag: EditText
        public lateinit var des: EditText
        public lateinit var reward: EditText
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new)
        userInit()

        val bt = findViewById<Button>(R.id.buttonAddHelp)

        componentsInit()

        bt.setOnClickListener {
            val title = this.viewComp.title.text.toString()
            val tag = this.viewComp.tag.text.toString()
            val des = this.viewComp.des.text.toString()
            val reward = this.viewComp.reward.text.toString()
            if(title==""||tag==""||des==""||reward==""){

            }else{
                d("new",tag)
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

    fun componentsInit(){
        this.viewComp = ViewComp()
        this.viewComp.title = findViewById<EditText>(R.id.title_edittext)
        this.viewComp.tag = findViewById<EditText>(R.id.tag_edittext)
        this.viewComp.des = findViewById<EditText>(R.id.description_edittext)
        this.viewComp.reward = findViewById<EditText>(R.id.reward_edittext)
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
        val tags = arrayOf(tag)
        val jsonObject = JSONObject().apply {
            put("title", title)
            put("tags", JSONArray(tags))
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
                    self.loadDone()
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
        this.viewComp.title.text.clear()
        this.viewComp.tag.text.clear()
        this.viewComp.des.text.clear()
        this.viewComp.reward.text.clear()
        Toast.makeText(this,"add successfully",Toast.LENGTH_LONG).show()
    }
}