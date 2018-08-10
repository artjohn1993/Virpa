package com.local.virpa.virpa.event

import android.R
import android.app.Activity
import android.support.design.widget.Snackbar
import android.support.design.widget.Snackbar.make
import android.view.View

class ShowSnackBar {
    companion object {
        fun present(data : String, activity: Activity) {
            val snackbar = make(activity.findViewById<View>(R.id.content), data, Snackbar.LENGTH_LONG)
            snackbar.show()
        }
    }
}