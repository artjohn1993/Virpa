package com.local.virpa.virpa.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.local.virpa.virpa.R

class ProfileAdapter : RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.layout_profile, parent, false)
        return ProfileViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {

    }

    class ProfileViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }
}