package com.xiong.aithelp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.widget.ImageView
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xiong.aithelp.dao.HelpDao
import com.xiong.aithelp.model.ApiModel
import com.xiong.aithelp.model.UserModel
import com.xiong.aithelp.service.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() ,ViewModelStoreOwner{
    private lateinit var user:UserModel
    private lateinit var api:ApiModel
//    private lateinit var myContext:Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userInit()

        val loginBt = findViewById<ImageView>(R.id.bt_user_center)
//        val sharedPref = this.getSharedPreferences(apiModel.dataFile, Context.MODE_PRIVATE)
//        val userModel = UserModel(sharedPref)
//        userModel.email = "elliot@asia.ait"
        val login = Intent(this,LoginActivity::class.java)
        loginBt.setOnClickListener {
            startActivity(login)
//            Toast.makeText(this, userModel.email, Toast.LENGTH_SHORT).show()
//            userModel.update()
        }

        val newBt = findViewById<ImageView>(R.id.bt_add_help)
        val newPage = Intent(this,NewActivity::class.java)
        newBt.setOnClickListener {
            startActivity(newPage)
        }

        loadList()
        checkLogin(login)
    }


    fun userInit(){
        this.api = ApiModel()
        val sharedPref = this.getSharedPreferences(this.api.dataFile, MODE_PRIVATE)
        val userModel = UserModel(sharedPref)
        this.user = userModel
    }

    fun loadList(){
        val apiModel = this.api
        val userModel = this.user
        val self = this
        val retrofit = Retrofit.Builder()
            .baseUrl(apiModel.address)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(ApiService::class.java)

        apiService.helpList().enqueue(object : Callback<List<HelpDao>> {
            override fun onResponse(call: Call<List<HelpDao>>, response: Response<List<HelpDao>>) {
                val helpList: List<HelpDao>? = response.body()
                if (response.code() == 200 && helpList != null) {
                    Log.d("http_help", helpList.get(0).user[0].username)
                    self.loadDone(helpList)
                }else{
                    //没有get到
                    Log.d("http_help", response.body().toString())
                }
            }

            override fun onFailure(call: Call<List<HelpDao>>, t: Throwable) {
                Log.d("http_help", t.message.toString())
            }
        })
    }

    fun loadDone(helpList:List<HelpDao>){
            val recyclerView = findViewById<RecyclerView>(R.id.help_list_container)
            val dataList:List<HelpDao> = helpList
            val adapter = HelpListAdapter(dataList,this)
            recyclerView?.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = adapter
    }

    override fun onDestroy() {
        d("destroy","destroy")
        this.user.empty()
        this.user.update()
        super.onDestroy()
    }

    fun checkLogin(loginPage: Intent) {
        if(!this.user.isAvailable()){
            startActivity(loginPage)
        }
    }
}