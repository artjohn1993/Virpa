package com.local.virpa.virpa.dialog

import android.app.Activity
import android.app.Dialog
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.ImageView
import com.local.virpa.virpa.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.toolbar_layout.*

class FullImageDialog(val activity: Activity) {
    var dialog : Dialog? = null
    var closeButton : ImageButton? = null
    var image : ImageView? = null

    fun show(link : String) {
        setDialog()

        closeButton?.setOnClickListener {
            dialog?.hide()
        }

        Picasso.get().load(link).into(image)
        dialog?.show()
    }

    fun setDialog() {
        dialog = Dialog(activity)
        dialog?.setContentView(R.layout.layout_full_image)
        dialog?.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT)
        dialog?.window?.setBackgroundDrawableResource(R.color.black_transparent)

        closeButton = dialog?.findViewById(R.id.fullImageClose)
        image = dialog?.findViewById(R.id.fullImage)
    }
}