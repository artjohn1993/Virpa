package com.local.virpa.virpa.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.local.virpa.virpa.R

class ListViewAdapter :  RecyclerView.Adapter<ListViewAdapter.ListViewViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val layout = inflater.inflate(R.layout.layout_list_view, parent, false)
        return ListViewViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return 10

    }

    override fun onBindViewHolder(holder: ListViewViewHolder, position: Int) {

    }

    class ListViewViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {

    }
}