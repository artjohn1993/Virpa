package com.local.virpa.virpa.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import android.widget.LinearLayout
import com.local.virpa.virpa.R
import com.local.virpa.virpa.adapter.SettingsAdapter
import com.local.virpa.virpa.api.VirpaApi
import com.local.virpa.virpa.dialog.Loading
import com.local.virpa.virpa.enum.LoginFragment
import com.local.virpa.virpa.enum.VirpaDB
import com.local.virpa.virpa.event.LoginChangeFragment
import com.local.virpa.virpa.event.ShowSnackBar
import com.local.virpa.virpa.event.SignOutEvent
import com.local.virpa.virpa.fragments.ForgetPassFragment
import com.local.virpa.virpa.fragments.SignInFragment
import com.local.virpa.virpa.fragments.SignupFragment
import com.local.virpa.virpa.local_db.DatabaseHandler
import com.local.virpa.virpa.model.SignIn
import com.local.virpa.virpa.model.SignOut
import com.local.virpa.virpa.presenter.SettingsPresenter
import com.local.virpa.virpa.presenter.SettingsPresenterClass
import com.local.virpa.virpa.presenter.SettingsView
import kotlinx.android.synthetic.main.activity_setting.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.startActivity

class SettingActivity : AppCompatActivity(), SettingsView {

    //region - Variables
    private val apiServer by lazy {
        VirpaApi.create(this)
    }
    var presenter = SettingsPresenterClass(this, apiServer)


    var loading = Loading(this)
    //endregion

    //region - Life Cycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        title = "Me"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        bind()
    }

    public override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    public override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
    //endregion

    //region - EventBus
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoginChangeFragment(event : SignOutEvent) {
        loading.show()
        var signout = SignOut.POST(userEmailSettings.text.toString())
        presenter.signOut(signout)
    }
    //endregion

    //region - Private methods
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
        val db = DatabaseHandler(this)
        var data = db.readSignResult()
        userNameSettings.text = data[0].user.detail.fullname
        userEmailSettings.text = data[0].user.detail.email
        userNumberSettings.text = data[0].user.detail.mobileNumber
    }
    //endregion

    override fun success(data: SignOut.Result) {
        var db = DatabaseHandler(this)
        db.deleteDatabase()
        loading.hide()
        startActivity<MainActivity>()
        finish()
    }

    override fun failed(data: String) {
        baseContext.deleteDatabase(VirpaDB.DATABASE_NAME.getValue())
        loading.hide()
        ShowSnackBar.present(data, this)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            android.R.id.home -> {
                this.finish()
            }
        }
        return true
    }
}
