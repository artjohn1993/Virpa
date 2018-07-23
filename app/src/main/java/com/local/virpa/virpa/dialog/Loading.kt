package com.local.virpa.virpa.dialog

import android.app.Activity
import android.app.Dialog
import android.view.ViewGroup
import com.local.virpa.virpa.R

class Loading(var activity: Activity) {

    var dialog : Dialog? = null

    fun show() {
        this.dialog = Dialog(activity)
        this.dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        this.dialog?.setContentView(R.layout.dialog_loading)
        this.dialog?.setCancelable(false)
        this.dialog?.show()
    }

    fun hide() {
        dialog?.hide()
    }
}