package com.local.virpa.virpa.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.local.virpa.virpa.R
import kotlinx.android.synthetic.main.activity_success.*
import org.jetbrains.anko.startActivity

class SuccessActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)
        nameSuccess.text = intent.getStringExtra("name")
        emailSuccess.text = intent.getStringExtra("email")

        successLayout.setOnClickListener {
            startActivity<MainActivity>()
            finish()
        }
    }
}
