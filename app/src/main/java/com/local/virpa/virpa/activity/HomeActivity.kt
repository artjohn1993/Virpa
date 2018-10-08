package com.local.virpa.virpa.activity

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_home.*
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import com.local.virpa.virpa.api.VirpaApi
import com.local.virpa.virpa.fragments.*
import com.local.virpa.virpa.model.Feed
import com.local.virpa.virpa.presenter.HomePresenterClass
import com.local.virpa.virpa.presenter.HomeView
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.toolbar_layout.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.startActivity
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.local.virpa.virpa.R
import com.local.virpa.virpa.dialog.Loading
import com.local.virpa.virpa.enum.FragmentType
import com.local.virpa.virpa.enum.publicToken
import com.local.virpa.virpa.event.Refresh
import com.local.virpa.virpa.local_db.DatabaseHandler
import com.local.virpa.virpa.model.TokenRefresh
import com.local.virpa.virpa.model.UserList
import com.local.virpa.virpa.presenter.TokenPresenterClass
import com.local.virpa.virpa.presenter.TokenView
import com.squareup.picasso.Picasso


class HomeActivity : AppCompatActivity(), HomeView, TokenView {


    //region - Variables
    var currentfragment : Int = 0
    private val apiServer by lazy {
        VirpaApi.create(this)
    }
    val presenter = HomePresenterClass(this, apiServer)
    val presenterToken = TokenPresenterClass(this, apiServer)
    private var compositeDisposable : CompositeDisposable = CompositeDisposable()
    var data : Feed.Result? = null
    var loading = Loading(this)
    var EXTERNAL_STORAGE_PERMISSION = 1
    var permission = android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    var permissionFineLoc = android.Manifest.permission.ACCESS_FINE_LOCATION
    var REQUEST_CODE = 1
    var permissionArray = arrayOf(permissionFineLoc)
    //endregion

    //region - Life Cycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.elevation = 0F
        setSupportActionBar(customToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        setProfile()
        changeFragment(FeedFragment(this, null, true), 1)
        checkStoragePermission()
        checkLocationPermission()
        refreshToken("session")
        navigationBar.disableShiftMode()
        navigationBar.setOnNavigationItemSelectedListener(
                BottomNavigationView.OnNavigationItemSelectedListener { item ->
                    when (item.itemId) {
                        R.id.feed -> {
                            try {
                                changeFragment(FeedFragment(this, this.data!!,false), 1)
                            } catch (e : Exception) {
                                changeFragment(FeedFragment(this, null, false), 1)
                        }
                            presenter.getMyFeed()
                            currentfragment = 1
                        }
                        R.id.location -> {
                            changeFragment(LocationFragment(this, null, true), 2)
                            presenter.getUserList()
                            currentfragment = 2
                        }
                        R.id.notif -> {
                            changeFragment(NotificationFragment(), 4)
                            currentfragment = 3
                        }
                        R.id.message -> {
                            changeFragment(MessageFragment(), 5)
                            currentfragment = 4
                        }
                    }
                    true
                })
        profilePicture.setOnClickListener {
            startActivity<SettingActivity>()
            finish()
        }

        /*Thread(Runnable {
            do {
                Handler().postDelayed({
                    refreshToken("session")
                },1500000)
            }while (true)
        }).start()*/

    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.clear()
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    //endregion

    //region - Private/public methods
    private fun changeFragment(data : android.support.v4.app.Fragment, selectedFragment : Int) {
        var fragment = supportFragmentManager.beginTransaction()

        if( currentfragment > selectedFragment) {
            fragment.setCustomAnimations(R.anim.abc_fade_in, R.anim.fade_out)
        }
        else if(currentfragment < selectedFragment) {
            fragment.setCustomAnimations(R.anim.abc_fade_in, R.anim.fade_out)
        }
        fragment.replace(R.id.homeFrameLayout, data).commit()
    }

    @SuppressLint("RestrictedApi")
    fun BottomNavigationView.disableShiftMode() {
        val menuView = getChildAt(0) as BottomNavigationMenuView
        try {
            val shiftingMode = menuView::class.java.getDeclaredField("mShiftingMode")
            shiftingMode.isAccessible = true
            shiftingMode.setBoolean(menuView, false)
            shiftingMode.isAccessible = false
            for (i in 0 until menuView.childCount) {
                val item = menuView.getChildAt(i) as BottomNavigationItemView
                item.setShiftingMode(false)
                // set once again checked value, so view will be updated
                item.setChecked(item.itemData.isChecked)
            }
        } catch (e: NoSuchFieldException) {

        }
    }

    private fun refreshToken(tokenType : String) {
        var data = DatabaseHandler(this).readSignResult()
        var email = data[0].user.detail.email
        var token  = data[0].authorization.refreshToken.token
        var tokenResource = TokenRefresh.TokenResource(tokenType, token)
        var postData = TokenRefresh.Post(email, tokenResource)
        presenterToken.refreshToken(postData)
    }
    fun checkStoragePermission() : Boolean {
        if(Build.VERSION.SDK_INT >= 23) {
            if( checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
                return true
            }
            else {
                ActivityCompat.requestPermissions(this, arrayOf(permission), EXTERNAL_STORAGE_PERMISSION)
                return false
            }
        }
        else {
            return true
        }
    }
    fun checkLocationPermission() {
        if(ContextCompat.checkSelfPermission(this, permissionFineLoc) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissionArray, REQUEST_CODE)
        }
    }
    private fun setProfile() {
        val db = DatabaseHandler(this)
        var data = db.readSignResult()
        if (data[0].user.profilePicture?.filePath != "") {
            Picasso.get().load(data[0].user.profilePicture?.filePath).into(profilePicture)
        }
    }
    //endregion

    //region - Presenter
    override fun feedResponse(data: Feed.Result) {
        this.data = data

        changeFragment(FeedFragment(this, data, false), 1)
    }

    override fun feedError(data: String) {

    }

    override fun refreshSuccess(data : TokenRefresh.Result) {
        if (data.succeed) {
            publicToken = data.data.authorization.token
            DatabaseHandler(this).updateRefresh(data.data.authorization.token,data.data.authorization.expiredAt)
            presenter.getMyFeed()
        }
    }

    override fun getUserListResponse(data: UserList.Result) {
        changeFragment(LocationFragment(this, data, false), 2)
    }

    //endregion

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onRefresh(event : Refresh) {
        when(event.type) {
            FragmentType.FEED -> {
                presenter.getMyFeed()
            }
            FragmentType.LOCATION -> {
                presenter.getUserList()
            }
        }
    }
}
