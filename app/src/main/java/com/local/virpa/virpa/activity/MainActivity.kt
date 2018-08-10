package com.local.virpa.virpa.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.local.virpa.virpa.fragments.SignInFragment
import com.local.virpa.virpa.R
import com.local.virpa.virpa.api.VirpaApi
import com.local.virpa.virpa.dialog.Loading
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
import com.local.virpa.virpa.fragments.ForgetPassFragment
import com.local.virpa.virpa.enum.LoginFragment
import com.local.virpa.virpa.event.*
import com.local.virpa.virpa.local_db.DatabaseHandler
import com.local.virpa.virpa.model.ForgetPass
import com.squareup.moshi.Moshi




class MainActivity : AppCompatActivity(), MainView {

    //region - Variables
    var loading = Loading(this)
    var db = DatabaseHandler(this)
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
    private fun successSignup(data : CreateUser.Result) {
        var intent = Intent(this, SuccessActivity::class.java)
        intent.putExtra("name", data.data.fullname)
        intent.putExtra("email", data.data.userName)
        startActivity(intent)
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
        if(event.type == LoginFragment.SIGNUP) {
            changeFragment(SignupFragment())
        }
        else if(event.type == LoginFragment.FORGET_PASSWORD) {
            changeFragment(ForgetPassFragment())

        }
        else {
            changeFragment(SignInFragment())
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onForgetPass(event : ForgetPassEvent) {
        var forgetPass = ForgetPass.Post(event.email)
        val moshi = Moshi.Builder().build()
        val jsonAdapter = moshi.adapter<ForgetPass.Post>(ForgetPass.Post::class.java)
        val json = jsonAdapter.toJson(forgetPass)
        println(json)
        try {
            presenter.forgetPass(forgetPass)
        }catch (e : Exception) {
            ShowSnackBar.present("Invalid email", this)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoginEvent(event : LoginEvent) {
        loading.show()
        if(event.username=="" || event.password=="") {
            loading.hide()
            snackBar("Please fill up correctly")
        }
        else {
            var data = SignIn.Request(
                    event.username,
                    event.password,
                    true
            )
            try{
                presenter.login(data)
            } catch (e : Exception) {
                snackBar("Invalid email or password")
            }

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

    //region - Presenter delegates
    override fun createSuccess(data : CreateUser.Result) {
        loading.hide()
        successSignup(data)
    }
    override fun loginSuccess(data: SignIn.Result) {
        var success = db.insertSignInResult(data)
        if(success) {
            loading.hide()
            nextActivity()
        }
        else {
            snackBar("Saving to local database failed")
        }
    }
    override fun loginFailed(data: String) {
        loading.hide()
        snackBar(data)
    }
    override fun createFailed(data: String) {
        loading.hide()
        snackBar(data)
    }
    override fun forgetPassSuccess(data: ForgetPass.Result) {
        ShowSnackBar.present("Check your email!", this)
        changeFragment(SignInFragment())
    }

    override fun forgetPassFailed(data: String) {
        ShowSnackBar.present(data, this)
    }
    //endregion
}


