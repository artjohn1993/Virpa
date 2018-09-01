package com.local.virpa.virpa.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.local.virpa.virpa.R
import com.local.virpa.virpa.api.VirpaApi
import com.local.virpa.virpa.model.SaveFiles
import com.local.virpa.virpa.presenter.EditInfoPresenterClass
import com.local.virpa.virpa.presenter.EditInfoView
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_edit_info.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.image
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class EditInfoActivity : AppCompatActivity(), EditInfoView , ActivityCompat.OnRequestPermissionsResultCallback{

    private val apiServer by lazy {
        VirpaApi.create(this)
    }
    val presenter = EditInfoPresenterClass(this, apiServer)
    var bitmapImage : Bitmap? = null
    var uri : Uri? = null
    var  path : String = ""
    private var compositeDisposable : CompositeDisposable = CompositeDisposable()
    private val REQUEST_WRITE_PERMISSION = 786

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_info)
        title = "Edit Information"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        changeID.setOnClickListener {
            requestPermission()
        }
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.clear()
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode === Activity.RESULT_OK) {
            if (requestCode === 1000) {
                this.uri = data?.data
                this.path = getPath(this, data?.data!!)
                println(path)

                bitmapImage = MediaStore.Images.Media.getBitmap(contentResolver, this.uri )
                attachment?.setImageBitmap(bitmapImage)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openFilePicker()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit_info_menu, menu)
        return true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            android.R.id.home -> {
                this.finish()
            }
            R.id.saveData -> {
                var file : File = File(this.path)
                var stream = ByteArrayOutputStream()
                bitmapImage?.compress(Bitmap.CompressFormat.PNG,20,stream)
                var byteArray = stream.toByteArray()

                profilePicture.setImageBitmap(bitmapImage)
                val reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), byteArray)
                val multiPart = MultipartBody.Part.createFormData("files", file.name, reqFile)
                presenter.saveFiles(multiPart)
            }
        }
        return true
    }

    override fun successSaveFiles(data : SaveFiles.Result) {

    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun convertBitmap(data : Bitmap)  : File {
        val filesDir = applicationContext.filesDir
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

    private fun openFilePicker() {
        var cameraIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(cameraIntent, 1000)
    }
    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_WRITE_PERMISSION)
        } else {
            openFilePicker()
        }
    }
}
