package com.xiong.aithelp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.xiong.aithelp.dao.CommentDao


class CommentAdapter(private val datalist: List<CommentDao>, private val context:Context): RecyclerView.Adapter<CommentAdapter.ViewHolder>(){

    inner class ViewHolder(view:View): RecyclerView.ViewHolder( view){
        var frameView = view.findViewById<LinearLayout>(R.id.comment_list_frame)
        val commentView: TextView  = view.findViewById(R.id.comment_detail)
        val userView: TextView = view.findViewById(R.id.comment_user)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comment_list_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val comment:CommentDao = datalist.get(position)
        holder.commentView.text = comment.content
        holder.userView.text = comment.user[0].username
    }
    override fun getItemCount(): Int {
        return datalist.size
    }


}