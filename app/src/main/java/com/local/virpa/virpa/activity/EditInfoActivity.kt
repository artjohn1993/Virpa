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
import com.bumptech.glide.Glide
import com.developers.imagezipper.ImageZipper
import com.local.virpa.virpa.R
import com.local.virpa.virpa.api.VirpaApi
import com.local.virpa.virpa.enum.OpenGallery
import com.local.virpa.virpa.event.ShowSnackBar
import com.local.virpa.virpa.model.ChangeProfile
import com.local.virpa.virpa.model.DeleteFiles
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

@Suppress("DEPRECATED_IDENTITY_EQUALS")
class EditInfoActivity : AppCompatActivity(), EditInfoView , ActivityCompat.OnRequestPermissionsResultCallback{

    private val apiServer by lazy {
        VirpaApi.create(this)
    }
    val presenter = EditInfoPresenterClass(this, apiServer)
    var bitmapImage : Bitmap? = null
    var uri : Uri? = null
    var  pathID : String = ""
    var  pathProfile : String = ""
    private var compositeDisposable : CompositeDisposable = CompositeDisposable()
    private val REQUEST_WRITE_PERMISSION = 786
    var fileID : String? = null
    var isChangeProfile = false
    var isChangeID = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_info)
        title = "Edit Information"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        presenter.getFiles()
        changeID.setOnClickListener {
            requestPermission(OpenGallery.ID)
        }
        changeProPic.setOnClickListener {
            requestPermission(OpenGallery.PROFILE)
        }

        deleteID.setOnClickListener {
            deleteFile()
        }
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.clear()
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode === Activity.RESULT_OK) {
            this.uri = data?.data
            bitmapImage = MediaStore.Images.Media.getBitmap(contentResolver, this.uri )
            if (requestCode === OpenGallery.ID.getValue()) {
                this.pathID = getPath(this, data?.data!!)
                attachment?.setImageBitmap(bitmapImage)
                isChangeID = true
            }
            else if (requestCode === OpenGallery.PROFILE.getValue()) {
                this.pathProfile = getPath(this, data?.data!!)
                profilePicture.setImageBitmap(bitmapImage)
                isChangeProfile = true
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == OpenGallery.ID.getValue() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openFilePicker(OpenGallery.ID)
        }
        else if (requestCode == OpenGallery.PROFILE.getValue() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openFilePicker(OpenGallery.PROFILE)
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
                if (isChangeID) {
                    saveID()
                }
                if (isChangeProfile) {
                    saveProfile()
                }
            }
        }
        return true
    }

    override fun successSaveFiles(data : SaveFiles.Result) {
        presenter.getFiles()
    }

    override fun successGetFiles(data: SaveFiles.Result) {
        fileID = data.data.files[0].id
        Glide.with(this)
                .load(data.data.files[0].filePath)
                .into(attachment)
    }

    override fun sucessDeleteFile(data: DeleteFiles.Result) {
        fileID = null
        attachment.setImageResource(R.drawable.ic_default_image)
    }

    override fun successProfile(data: ChangeProfile.Result) {
        ShowSnackBar.present("Profile Save" , this)
    }

    private fun saveID() {
        var imageArray : ArrayList<SaveFiles.FilesInfo> = ArrayList()
        var file : File = File(this.pathID)
        var base64 = ImageZipper.getBase64forImage(file).toString()
        var files = SaveFiles.FilesInfo(file.name,base64)
        imageArray.add(files)
        var savePost = SaveFiles.Post(imageArray)
        presenter.saveFiles(savePost)
    }

    private fun saveProfile() {
        var file : File = File(this.pathProfile)
        var base64 = ImageZipper.getBase64forImage(file).toString()
        var data = ChangeProfile.File(file.name,base64)
        presenter.changeProfile(ChangeProfile.Post(data))
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
    private fun deleteFile() {
        var dataArray : ArrayList<DeleteFiles.Files> = ArrayList()
        var data = DeleteFiles.Files(this.fileID!!)
        dataArray.add(data)
        presenter.deleteFile(DeleteFiles.Post(dataArray))
    }
    private fun openFilePicker(data : OpenGallery) {
        var intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, data.getValue())

    }
    private fun requestPermission(data : OpenGallery) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), data.getValue())
        } else {
            openFilePicker(data)
        }
    }
}
