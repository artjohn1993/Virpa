package com.local.virpa.virpa.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.widget.LinearLayout
import com.local.virpa.virpa.R
import com.local.virpa.virpa.adapter.SettingsAdapter
import com.local.virpa.virpa.local_db.DatabaseHandler
import kotlinx.android.synthetic.main.activity_setting.*
import org.jetbrains.anko.startActivity

class SettingActivity : AppCompatActivity() {

    val db = DatabaseHandler(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        title = "Me"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        bind()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            android.R.id.home -> {
                this.finish()
            }
        }
        return true
    }
    private fun bind() {
        setRecycler()
        scrollTop()
        setProfile()
    }
    private fun scrollTop() {
        settingScrollView.smoothScrollTo(0,0)
    }
    private fun setRecycler() {
        settingsRecycler.layoutManager = LinearLayoutManager(this,
                LinearLayout.VERTICAL,
                false)
        settingsRecycler.adapter = SettingsAdapter(this)
        settingsRecycler.isFocusable = false
    }
    private fun setProfile() {
        var data = db.readSignResult()
        userNameSettings.text = data[0].user.fullname
        userEmailSettings.text = data[0].user.email
        userNumberSettings.text = data[0].user.mobileNumber
    }
}
