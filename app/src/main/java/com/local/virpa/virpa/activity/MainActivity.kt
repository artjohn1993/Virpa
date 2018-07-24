package com.local.virpa.virpa.activity

import android.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.local.virpa.virpa.fragments.SignInFragment
import com.local.virpa.virpa.R
import com.local.virpa.virpa.api.VirpaApi
import com.local.virpa.virpa.dialog.Loading
import com.local.virpa.virpa.event.LoginChangeFragment
import com.local.virpa.virpa.event.LoginEvent
import com.local.virpa.virpa.event.RegisterEvent
import com.local.virpa.virpa.fragments.SignupFragment
import com.local.virpa.virpa.model.CreateUser
import com.local.virpa.virpa.model.SignIn
import com.local.virpa.virpa.presenter.MainPresenter
import com.local.virpa.virpa.presenter.MainPresenterClass
import com.local.virpa.virpa.presenter.MainView
import io.reactivex.disposables.CompositeDisposable
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.startActivity
import android.support.design.widget.Snackbar
import android.view.View


class MainActivity : AppCompatActivity(), MainView {

    //region - Variables
    var loading = Loading(this)
    private val apiServer by lazy {
        VirpaApi.create(this)
    }
    val presenter : MainPresenter = MainPresenterClass(this, apiServer)
    private var compositeDisposable : CompositeDisposable = CompositeDisposable()
    //endregion

    //region - Life Cycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        //fragment.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_in)
        changeFragment(SignInFragment())
    }

    public override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.clear()
    }

    public override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)

    }
    //endregion

    //region - Private Methods
    private fun changeFragment(data : android.support.v4.app.Fragment) {
        var fragment = supportFragmentManager.beginTransaction()
        fragment.setCustomAnimations(R.anim.abc_slide_in_bottom, android.R.animator.fade_out)
        fragment.replace(R.id.frameContainer, data).commit()
    }

    private fun nextActivity() {
        startActivity<HomeActivity>()
        finish()
    }
    private fun snackBar(data : String) {
        val snackbar = Snackbar.make(findViewById<View>(android.R.id.content), data, Snackbar.LENGTH_LONG)
        snackbar.show()
    }
    //endregion

    //region - EventBus Method
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoginChangeFragment(event : LoginChangeFragment) {
        if(event.type == "signup") {
            changeFragment(SignupFragment())
        }
        else {
            changeFragment(SignInFragment())
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoginEvent(event : LoginEvent) {
        loading.show()
        if(event.username=="" || event.password=="") {
            snackBar("Please fill up correctly")
        }
        else {
            var data = SignIn.Request(
                    event.username,
                    event.password,
                    true
            )
            presenter.login(data)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onRegisterEvent(event : RegisterEvent) {
        loading.show()
        var data = CreateUser.Post(
                event.fullname,
                event.mobilenumber,
                event.email,
                event.password
        )
        presenter.createUser(data)
    }
    //endregion

    override fun createSuccess(data : CreateUser.Result) {
        loading.hide()
        snackBar(data.message[0])
        nextActivity()
    }
    override fun loginSuccess(data: SignIn.Result) {
        loading.hide()
        nextActivity()
    }
    override fun loginFailed(data: String) {
        loading.hide()
        snackBar(data)
    }
    override fun createFailed(data: String) {
        loading.hide()
        snackBar(data)
    }
}

