package com.local.virpa.virpa.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView

import com.local.virpa.virpa.R
import android.graphics.Bitmap
import android.R.attr.data
import android.app.Activity.RESULT_OK


@Suppress("DEPRECATED_IDENTITY_EQUALS")
class PostFragment : Fragment() {
    var type : RadioButton? = null
    var group : RadioGroup? = null
    var body : EditText? = null
    var budget : EditText? = null
    var image : TextView? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_post, container, false)
        setVar(view)

        image?.setOnClickListener {
            var cameraIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivity(cameraIntent)
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode === RESULT_OK) {
            if (requestCode === 1000) {
                val returnUri = data?.data
                //val bitmapImage = MediaStore.Images.Media.getBitmap(getActivity.getContentResolver(), returnUri)
            }
        }
    }

    private fun setVar(view : View) {
        group = view.findViewById(R.id.postType)
        type = view.findViewById(group?.checkedRadioButtonId!!)
        body = view.findViewById(R.id.postBody)
        budget = view.findViewById(R.id.postBudget)
        image  = view.findViewById(R.id.image)
    }

}
