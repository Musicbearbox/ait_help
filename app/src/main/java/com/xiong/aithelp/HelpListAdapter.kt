package com.xiong.aithelp

import android.content.Context
import android.content.Intent
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.xiong.aithelp.dao.HelpDao

class HelpListAdapter(private val datalist: List<HelpDao> ,private val context:Context): RecyclerView.Adapter<HelpListAdapter.ViewHolder>(){

    inner class ViewHolder(view:View): RecyclerView.ViewHolder( view){


        var frameView = view.findViewById<LinearLayout>(R.id.help_list_frame)
        val titleView: TextView  = view.findViewById(R.id.help_list_title)
        val tagView: TextView = view.findViewById(R.id.help_list_tag)
        val usernameView: TextView = view.findViewById(R.id.help_list_username)
        val rewardView: TextView = view.findViewById(R.id.help_list_reward)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HelpListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.help_list_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: HelpListAdapter.ViewHolder, position: Int) {

        val help:HelpDao = datalist.get(position)
        holder.titleView.text = help.title
        if(help.tags.size!=0){
            holder.tagView.text = help.tags.get(0)
        }else{
            holder.tagView.text = "default"
        }
        holder.usernameView.text = help.user[0].username
        holder.rewardView.text = help.reward
        holder.frameView.setOnClickListener {
            val help:HelpDao = datalist.get(position)
            val id = help.id
            d("click",id)
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("title", id)
            context.startActivity(intent)
        }
    }
    override fun getItemCount(): Int {
        return datalist.size
    }


}