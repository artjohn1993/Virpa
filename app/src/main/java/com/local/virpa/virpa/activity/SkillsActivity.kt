package com.local.virpa.virpa.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import com.local.virpa.virpa.R
import com.local.virpa.virpa.adapter.SkillsAdapter
import com.local.virpa.virpa.api.VirpaApi
import com.local.virpa.virpa.dialog.Loading
import com.local.virpa.virpa.event.CheckSkillsEvent
import com.local.virpa.virpa.event.ShowSnackBar
import com.local.virpa.virpa.event.SignOutEvent
import com.local.virpa.virpa.model.*
import com.local.virpa.virpa.presenter.SettingsPresenterClass
import com.local.virpa.virpa.presenter.SkillsPresenterClass
import com.local.virpa.virpa.presenter.SkillsView
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.activity_skills.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class SkillsActivity : AppCompatActivity(), SkillsView {

    //region - Variables
    private val apiServer by lazy {
        VirpaApi.create(this)
    }
    var presenter = SkillsPresenterClass(this, apiServer)
    var loading = Loading(this)
    private var compositeDisposable : CompositeDisposable = CompositeDisposable()
    var skill : ArrayList<SaveMySkills.SkillsSet> = ArrayList()
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skills)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        title = "Set Skills"
        presenter.getSkills()
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.clear()
    }
    public override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    public override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            android.R.id.home -> {
                this.finish()
            }
            R.id.saveData -> {
                var data = SaveMySkills.Post(skill)
                presenter.saveMySkills(data)
            }
        }
        return true
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit_info_menu, menu)
        return true
    }
    override fun skills(skill: Skills.Result, mySkills: MySkills.Result) {
        skillsRecycler.layoutManager = LinearLayoutManager(this,
                LinearLayout.VERTICAL,
                false)
        skillsRecycler.adapter = SkillsAdapter(skill, mySkills)
        setSkills(mySkills)
    }
    override fun saveSkillsResponse(data: MySkills.Result) {
        ShowSnackBar.present("Save Skills", this)
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onCheck(event : CheckSkillsEvent) {
        var info = SaveMySkills.SkillsSet(event.id,  event.title)
        addSkills(info, event.check)
    }

    private fun setSkills(mySkills: MySkills.Result) {
        for(item in mySkills.data.skills) {
            var info = SaveMySkills.SkillsSet(item.id, item.name)
            addSkills(info, true)
        }
    }
    private fun addSkills(data : SaveMySkills.SkillsSet, check : Boolean) {
        if(!skill.contains(data) && check) {
            skill.add(data)
        }
        else if(skill.contains(data) && !check) {
            skill.remove(data)
        }
    }
}
