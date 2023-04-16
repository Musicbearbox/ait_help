package com.xiong.aithelp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.xiong.aithelp.model.ApiModel
import com.xiong.aithelp.model.UserModel

class MainActivity : AppCompatActivity() ,ViewModelStoreOwner{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val apiModel = ApiModel()

        val loginBt = findViewById<ImageView>(R.id.bt_user_center)
//        val sharedPref = this.getSharedPreferences(apiModel.dataFile, Context.MODE_PRIVATE)
//        val userModel = UserModel(sharedPref)
//        userModel.email = "elliot@asia.ait"

        loginBt.setOnClickListener {
            val login = Intent(this,LoginActivity::class.java)
            startActivity(login)
//            Toast.makeText(this, userModel.email, Toast.LENGTH_SHORT).show()
//            userModel.update()
        }

        val recyclerView = findViewById<RecyclerView>(R.id.help_list_container)
        val dataList = listOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")
        val adapter = HelpListAdapter(dataList)
        recyclerView.adapter = adapter
    }


}