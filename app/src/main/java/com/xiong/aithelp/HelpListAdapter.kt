package com.xiong.aithelp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class HelpListAdapter(private val datalist: List<String> ): RecyclerView.Adapter<HelpListAdapter.ViewHolder>(){

    inner class ViewHolder(itemView:View): RecyclerView.ViewHolder( itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HelpListAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.help_list_layout,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: HelpListAdapter.ViewHolder, position: Int) {

    }
    override fun getItemCount(): Int {
        return datalist.size
    }
}