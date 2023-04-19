package com.xiong.aithelp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xiong.aithelp.dao.CommentDao
import com.xiong.aithelp.dao.CommentNewResponseDao
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

class DetailActivity : AppCompatActivity() {
    private lateinit var user: UserModel
    private lateinit var api: ApiModel
    private lateinit var viewComp: ViewComp
    private var id: String = ""
    public var isLoaded:Boolean = false
    public var userType:Int = 2  //1:poster 2:taker
    public var postUserId:String = ""
    public var taker:String = ""

    class ViewComp{
        public lateinit var title: TextView
        public lateinit var tag: TextView
        public lateinit var user: TextView
        public lateinit var createTime: TextView
        public lateinit var reward: TextView
        public lateinit var des: TextView
        public lateinit var solveOrAcceptBt: Button

        public lateinit var commentFrame: RecyclerView
        public lateinit var commentInput: EditText
        public lateinit var commentSubmit: Button
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        userInit()
        componentsInit()

        checkLogin()

        val dataSet = intent.extras
        if(this.id == ""){
            this.id = dataSet?.getString("id").toString()
        }
        getDetail(this.id)
        loadCommentList(this.id)
        this.viewComp.commentSubmit.setOnClickListener {
            val comment = this.viewComp.commentInput.text.toString()
            if(comment != ""&& this.id!=""){
                commentCreate(this.id,comment)
            }else{
                Toast.makeText(this,"waiting",Toast.LENGTH_LONG).show()

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
        this.viewComp.title = findViewById<TextView>(R.id.dt_title_text)
        this.viewComp.tag = findViewById<TextView>(R.id.dt_tag_text)
        this.viewComp.user = findViewById<TextView>(R.id.dt_user_text)
        this.viewComp.createTime = findViewById<TextView>(R.id.dt_time_text)
        this.viewComp.reward = findViewById<TextView>(R.id.dt_reward_text)
        this.viewComp.des = findViewById<TextView>(R.id.dt_des_text)
        this.viewComp.commentFrame = findViewById<RecyclerView>(R.id.dt_view_container)
        this.viewComp.commentInput = findViewById(R.id.dt_comment_edit)
        this.viewComp.commentSubmit = findViewById(R.id.bt_comment_publish)

        this.viewComp.solveOrAcceptBt = findViewById(R.id.button_solve)
    }

    fun getDetail(id:String){
        val apiModel = this.api
        val self = this
        val retrofit = Retrofit.Builder()
            .baseUrl(apiModel.address)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(ApiService::class.java)

        val jsonObject = JSONObject().apply {
            put("id", id)
            put("token", self.user.token)
        }
        val requestBody = jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())
        apiService.detailHelp(requestBody).enqueue(object : Callback<HelpDao> {
            override fun onResponse(call: Call<HelpDao>, response: Response<HelpDao>) {
                val help: HelpDao? = response.body()
                if (response.code() == 200 && help != null) {
                    Log.d("http_help_detail", help.title)
                    self.loadDone(help)
                }else{
                    //没有get到
                    Log.d("http_help_detail", response.body().toString())
                }
            }

            override fun onFailure(call: Call<HelpDao>, t: Throwable) {
                Log.d("http_help_detail", t.message.toString())
            }
        })
    }

    fun loadCommentList(id:String){
        d("http_comment_param",id)
        val apiModel = this.api
        val self = this
        val retrofit = Retrofit.Builder()
            .baseUrl(apiModel.address)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(ApiService::class.java)

        val jsonObject = JSONObject().apply {
            put("postId", id)
            put("token", self.user.token)
        }
        val requestBody = jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())
        val caller = apiService.commentList(requestBody)
        caller.enqueue(object : Callback<List<CommentDao>> {
            override fun onResponse(call: Call<List<CommentDao>>, response: Response<List<CommentDao>>) {
                val commentList: List<CommentDao>? = response.body()
                if (response.code() == 200 && commentList != null) {
//                    Log.d("http_comment", commentList.get(0).user[0].username)
                    self.commentLoadDome(commentList)
                }else{
                    //没有get到
                    Log.d("http_comment_empty", response.message())
                }
            }

            override fun onFailure(call: Call<List<CommentDao>>, t: Throwable) {
                Log.d("http_comment_error", t.message.toString())
            }
        })
    }

    fun loadDone(help:HelpDao){
        this.viewComp.title.text = help.title
        this.viewComp.tag.text = help.tags[0]
        this.viewComp.user.text = help.user[0].username
        this.viewComp.reward.text = help.reward
        this.viewComp.createTime.text = help.createdAt
        this.viewComp.des.text = help.description

        this.postUserId = help.user[0].id
        this.taker = help.taker
        this.isLoaded = true
        this.buttonStutasChange()

    }

    fun commentLoadDome(commentList:List<CommentDao>){
        val recyclerView = findViewById<RecyclerView>(R.id.dt_view_container)
        val dataList:List<CommentDao> = commentList
        val adapter = CommentAdapter(dataList,this)
        recyclerView?.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    fun commentCreate(id:String,comment:String){
        d("http_comment_param",id)
        val apiModel = this.api
        val self = this
        val retrofit = Retrofit.Builder()
            .baseUrl(apiModel.address)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(ApiService::class.java)

        val jsonObject = JSONObject().apply {
            put("postId", id)
            put("content",comment)
            put("token", self.user.token)
        }
        val requestBody = jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())
        val caller = apiService.newComment(requestBody)
        caller.enqueue(object : Callback<CommentNewResponseDao> {
            override fun onResponse(call: Call<CommentNewResponseDao>, response: Response<CommentNewResponseDao>) {
                val commentResponse: CommentNewResponseDao? = response.body()
                if (response.code() == 200 && commentResponse != null) {
                    Log.d("http_comment_new", commentResponse.id)
                    self.commentCreatDone()
                }else{
                    //没有get到
                    Log.d("http_comment_new_empty", response.message())
                }
            }

            override fun onFailure(call: Call<CommentNewResponseDao>, t: Throwable) {
                Log.d("http_comment_new_error", t.message.toString())
            }
        })
    }

    fun commentCreatDone(){
        this.viewComp.commentInput.text.clear()
        this.loadCommentList(this.id)
    }

    fun buttonStutasChange(){
        if(this.isLoaded){
            if(this.user.userId == this.postUserId){
                //self
                if(this.taker != ""){
                    this.viewComp.solveOrAcceptBt.setVisibility(View.VISIBLE);
                    this.viewComp.solveOrAcceptBt.text = "Solve It"
                    this.viewComp.solveOrAcceptBt.setOnClickListener {
                        this.viewComp.solveOrAcceptBt.setVisibility(View.INVISIBLE);
                        Toast.makeText(this,"Solved successfully",Toast.LENGTH_LONG).show()
                    }
                }
            }else{
                if(this.taker == ""){
                    this.viewComp.solveOrAcceptBt.setVisibility(View.VISIBLE);
                    this.viewComp.solveOrAcceptBt.text = "Accept It"
                    this.viewComp.solveOrAcceptBt.setOnClickListener {
                        this.viewComp.solveOrAcceptBt.setVisibility(View.INVISIBLE);
                        Toast.makeText(this,"Accepted successfully",Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    fun checkLogin(){
        if(!this.user.isAvailable()){
            val login = Intent(this,LoginActivity::class.java)
            startActivity(login)
        }
    }
}