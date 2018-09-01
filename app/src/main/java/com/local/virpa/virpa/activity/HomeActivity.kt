package com.local.virpa.virpa.activity

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.local.virpa.virpa.R
import kotlinx.android.synthetic.main.activity_home.*
import android.support.annotation.NonNull
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.ActionBar
import android.util.Log
import android.view.Menu
import android.widget.Toolbar
import com.local.virpa.virpa.api.VirpaApi
import com.local.virpa.virpa.enum.LoginFragment
import com.local.virpa.virpa.event.LoginChangeFragment
import com.local.virpa.virpa.event.PostEvent
import com.local.virpa.virpa.event.ShowSnackBar
import com.local.virpa.virpa.fragments.*
import com.local.virpa.virpa.model.Feed
import com.local.virpa.virpa.model.SaveFeed
import com.local.virpa.virpa.presenter.HomePresenterClass
import com.local.virpa.virpa.presenter.HomeView
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.toolbar_layout.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.startActivity
import android.R.attr.bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Handler
import android.support.annotation.RequiresApi
import com.developers.imagezipper.ImageZipper
import com.local.virpa.virpa.R.id.name
import com.local.virpa.virpa.dialog.Loading
import com.local.virpa.virpa.enum.RequestError
import com.local.virpa.virpa.enum.publicToken
import com.local.virpa.virpa.local_db.DatabaseHandler
import com.local.virpa.virpa.model.TokenRefresh
import com.local.virpa.virpa.presenter.TokenPresenterClass
import com.local.virpa.virpa.presenter.TokenView
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.Error


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
        refreshToken("session")
        navigationBar.disableShiftMode()
        navigationBar.setOnNavigationItemSelectedListener(
                BottomNavigationView.OnNavigationItemSelectedListener { item ->
                    when (item.itemId) {
                        R.id.feed -> {
                            changeFragment(FeedFragment(this, this.data!!), 1)
                            currentfragment = 1
                        }
                        R.id.location -> {
                            changeFragment(LocationFragment(this), 2)
                            currentfragment = 2
                        }
                        R.id.post -> {
                            changeFragment(PostFragment(this), 3)
                            currentfragment = 3

                        }
                        R.id.notif -> {
                            changeFragment(NotificationFragment(), 4)
                            currentfragment = 4
                        }
                        R.id.message -> {
                            changeFragment(MessageFragment(), 5)
                            currentfragment = 5
                        }
                    }
                    true
                })
        profilePicture.setOnClickListener {
            startActivity<SettingActivity>()
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

        } catch (e: IllegalStateException) {

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
    //endregion

    //region - Presenter
    override fun feedResponse(data: Feed.Result) {
        this.data = data
        changeFragment(FeedFragment(this, this.data!!), 1)
    }

    override fun feedError(data: String) {

    }

    override fun saveFeedResponse(data: SaveFeed.Result) {
        loading.hide()
    }

    override fun saveFeedError(data: String) {
        loading.hide()
    }
    override fun refreshSuccess(data : TokenRefresh.Result) {
        if (data.succeed) {
            publicToken = data.data.token
            DatabaseHandler(this).updateRefresh(data.data.token,data.data.expiredAt)
            presenter.getMyFeed()
        }
    }

    //endregion

    //region - EventBus
    @RequiresApi(Build.VERSION_CODES.O)
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onPostEvent(event : PostEvent) {
        loading.show()
        var file = File(event.path)
        var base64 = ImageZipper.getBase64forImage(file)
        val saveFeed = SaveFeed.PostCoverPhoto(file.name, base64)
        presenter.saveMyFeed(SaveFeed.Post("0",0, event.body,event.budget,3, saveFeed))
    }
    //endregion
}
