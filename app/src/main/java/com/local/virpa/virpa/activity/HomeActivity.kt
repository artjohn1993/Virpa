package com.local.virpa.virpa.activity

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_home.*
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import com.local.virpa.virpa.api.VirpaApi
import com.local.virpa.virpa.fragments.*
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
import android.view.LayoutInflater
import android.view.View
import com.google.firebase.database.*
import com.google.firebase.iid.FirebaseInstanceId
import com.local.virpa.virpa.R
import com.local.virpa.virpa.adapter.NotificationAdapter
import com.local.virpa.virpa.dialog.Loading
import com.local.virpa.virpa.enum.FragmentType
import com.local.virpa.virpa.enum.publicFKey
import com.local.virpa.virpa.enum.publicToken
import com.local.virpa.virpa.event.FirebaseNotify
import com.local.virpa.virpa.event.Refresh
import com.local.virpa.virpa.local_db.DatabaseHandler
import com.local.virpa.virpa.model.*
import com.local.virpa.virpa.presenter.TokenPresenterClass
import com.local.virpa.virpa.presenter.TokenView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.layout_notification_badge.*
import java.util.ArrayList


class HomeActivity : AppCompatActivity(), HomeView, TokenView {


    //region - Variables
    var currentfragment : Int = 0
    private val apiServer by lazy {
        VirpaApi.create(this)
    }
    var CHANNEL_ID = "SAMPLE_CHANNEL"
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
    var database = DatabaseHandler(this)
    var notifArray : MutableList<FirebaseModel.Response> = ArrayList()
    var randomNotifIDArray : ArrayList<String> = ArrayList()
    var isNotificationClick = false
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
        initFCM()
        getkey()
        notification()
        setNotification()
        changeFragment(FeedFragment(this, null, true), 1)
        checkStoragePermission()
        checkLocationPermission()
        refreshToken("session")
        navigationBar.disableShiftMode()

        navigationBar.setOnNavigationItemSelectedListener { item ->
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
                    isNotificationClick = true
                    seenNotif()
                    changeFragment(NotificationFragment(this, notifArray), 4)
                    currentfragment = 3

                }
                R.id.message -> {
                    changeFragment(MessageFragment(), 5)
                    currentfragment = 4
                }
            }
            true
        }
        profilePicture.setOnClickListener {
            startActivity<SettingActivity>()
            finish()
        }
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
                item.setShifting(false)
                // set once again checked value, so view will be updated
                item.setChecked(item.itemData.isChecked)
            }
        } catch (e: NoSuchFieldException) {

        }
    }
    private fun notification() {
        var notif = FirebaseDatabase.getInstance().reference
                .child("notification")
                .child(database.readSignResult()[0].user.detail.id)

         notif.addChildEventListener( object : ChildEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onChildMoved(p0: DataSnapshot, p1: String?) {

                }

                override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                    println(p1)
                    if(isNotificationClick) {
                        setData(p0)
                        randomNotifIDArray.add(p0.key!!)
                    }
                }

                override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                    println(p1)
                    if(!isNotificationClick) {
                        setData(p0)
                        randomNotifIDArray.add(p0.key!!)
                    }
                }

                override fun onChildRemoved(p0: DataSnapshot) {

                }

            })
    }
    private fun setData(data : DataSnapshot?) {
        var i = data!!.children.iterator()
        while (i.hasNext()) {

            var action = i.next().value.toString()
            var activity = i.next().value.toString()
            var data = i.next().value.toString()
            var description = i.next().value.toString()
            var name = i.next().value.toString()
            var seen = i.next().value.toString()
            var time = i.next().value.toString()
            var info = FirebaseNotify().fromJson(data)
            var intent = FirebaseModel.Intent(info.bidderID,
            info.feedID,
            info.feederID,
            info.threadID
            )

            var dataArray = FirebaseModel.Response(
                    activity,
                    description,
                    name,
                    seen,
                    time,
                    action,
                    intent
            )
            if(!notifArray.contains(dataArray)) {
                notifArray.add(dataArray)
            }
        }
        checkNotifArray(notifArray)
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
    private fun initFCM() {
        var token = FirebaseInstanceId.getInstance().getToken()
        println(token)
        updateToken(token)
    }
    private fun updateToken(token : String?) {
        FirebaseDatabase.getInstance().reference
                .child("user")
                .child(DatabaseHandler(this).readSignResult()[0].user.detail.id)
                .child("token")
                .setValue(token).addOnCompleteListener {
                    println("token updated")
                }
    }
    private fun getkey() {
        var root = FirebaseDatabase.getInstance().reference
        var query = root.child("server")
                .orderByValue()

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                publicFKey = p0.children.iterator().next().value.toString()
            }

        })
    }
    private fun  checkNotifArray(data : MutableList<FirebaseModel.Response>) {
        var total = 0

       for (item in data) {
           if (item.seen == "false") {
                  total++
           }
       }
        if (total in 1..9) {
            //notificationBadge.text = " $total "
            notificationBadge.text = "   $total   "
        }
        else if(total in 10..99) {
            notificationBadge.text = "  $total  "
        }
        else {
            notificationBadge.text = " $total "
        }

        if (total == 0) {
            notificationBadge.visibility = View.GONE
        }
        else {
            notificationBadge.visibility = View.VISIBLE
        }
    }
    private fun setNotification() {
        val bottomNavigationMenuView = navigationBar.getChildAt(0) as BottomNavigationMenuView
        val v = bottomNavigationMenuView.getChildAt(2)
        val itemView = v as BottomNavigationItemView

        val badge = LayoutInflater.from(this)
                .inflate(R.layout.layout_notification_badge, bottomNavigationMenuView, false)

        itemView.addView(badge)
    }
    private fun seenNotif() {
        notificationBadge.visibility = View.GONE

        var notif = FirebaseDatabase.getInstance().reference
                .child("notification")
                .child(database.readSignResult()[0].user.detail.id)

        for (item in randomNotifIDArray) {
            notif.child(item)
                    .child("seen")
                    .setValue("true")
        }
        randomNotifIDArray.clear()
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
