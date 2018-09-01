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

import com.local.virpa.virpa.R
import android.graphics.Bitmap
import android.R.attr.data
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.Log
import android.widget.*
import com.local.virpa.virpa.event.PostEvent
import org.greenrobot.eventbus.EventBus
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@SuppressLint("ValidFragment")
@Suppress("DEPRECATED_IDENTITY_EQUALS")
class PostFragment @SuppressLint("ValidFragment") constructor
(val activity: Activity) : Fragment() {
    var type : RadioButton? = null
    var group : RadioGroup? = null
    var body : EditText? = null
    var budget : EditText? = null
    var image : TextView? = null
    var captureImage : ImageView? = null
    var postButton : Button? = null
    var bitmapImage : Bitmap? = null
    var path : String = ""


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_post, container, false)
        setVar(view)

        image?.setOnClickListener {
            var cameraIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(cameraIntent, 1000)
        }
        postButton?.setOnClickListener {
            EventBus.getDefault().post(PostEvent("0", body?.text.toString(), budget?.text.toString().toDouble(), path))
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode === RESULT_OK) {
            if (requestCode === 1000) {
                val returnUri = data?.data
                bitmapImage = MediaStore.Images.Media.getBitmap(activity!!.contentResolver, returnUri)
                path = getPath(activity , data?.data!!)
                captureImage?.setImageBitmap(bitmapImage)
                captureImage?.visibility = View.VISIBLE
            }
        }
    }
    fun getPath(context: Context, uri: Uri): String {
        var result: String? = null
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(uri, proj, null, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val column_index = cursor.getColumnIndexOrThrow(proj[0])
                result = cursor.getString(column_index)
            }
            cursor.close()
        }
        if (result == null) {
            result = "Not found"
        }
        return result
    }

    private fun setVar(view : View) {
        group = view.findViewById(R.id.postType)
        type = view.findViewById(group?.checkedRadioButtonId!!)
        body = view.findViewById(R.id.postBody)
        budget = view.findViewById(R.id.postBudget)
        image  = view.findViewById(R.id.image)
        captureImage  = view.findViewById(R.id.postLocalImage)
        postButton  = view.findViewById(R.id.postButton)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun convertBitmap(data : Bitmap)  : File {
        val filesDir = activity.applicationContext.filesDir
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")
        val formatted = LocalDateTime.now().format(formatter)
        val imageFile = File(filesDir, "$formatted.jpg")

        val os : OutputStream
        try {
            os = FileOutputStream(imageFile) as OutputStream
            data.compress(Bitmap.CompressFormat.JPEG, 100, os)
            os.flush()
            os.close()
        } catch (e: Exception) {
            Log.e(javaClass.simpleName, "Error writing bitmap", e)
        }
        return imageFile
    }

}
