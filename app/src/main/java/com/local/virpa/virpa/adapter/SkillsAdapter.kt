package com.local.virpa.virpa.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import com.local.virpa.virpa.R
import com.local.virpa.virpa.event.CheckSkillsEvent
import com.local.virpa.virpa.model.MySkills
import com.local.virpa.virpa.model.Skills
import org.greenrobot.eventbus.EventBus

class SkillsAdapter(var skill: Skills.Result,var mySkills: MySkills.Result) : RecyclerView.Adapter<SkillsAdapter.SkillsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillsViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val layout = inflater.inflate(R.layout.layout_skills, parent, false)
        return SkillsViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return skill.data.skills.size
    }

    override fun onBindViewHolder(holder: SkillsViewHolder, position: Int) {
        var id = skill.data.skills[position].id
        var title = skill.data.skills[position].name
        holder.title.text = skill.data.skills[position].name

        holder.box.setOnCheckedChangeListener { buttonView, isChecked ->
            EventBus.getDefault().post(CheckSkillsEvent(id, title, isChecked))
        }

        for (item in mySkills.data.skills) {
            if (skill.data.skills[position].id == item.id) {
                holder.box.isChecked = true
            }
        }

    }

    class SkillsViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        var title = view.findViewById<TextView>(R.id.skillsTitle)
        var box = view.findViewById<android.support.v7.widget.AppCompatCheckBox>(R.id.skillsBox)
    }
}