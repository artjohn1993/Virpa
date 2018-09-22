package com.local.virpa.virpa.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.local.virpa.virpa.R
import com.local.virpa.virpa.local_db.DatabaseHandler
import org.jetbrains.anko.startActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        try {
            Handler().postDelayed({
                var db = DatabaseHandler(this)
                var login = db.checkSignInResult()
                if (login) {
                    startActivity<HomeActivity>()
                    finish()
                } else {
                    startActivity<MainActivity>()
                    finish()
                }


            }, 2000)
        }catch (e : Exception) {
            println(e.toString())
        }
    }
}
