package com.local.virpa.virpa.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.developers.imagezipper.ImageZipper
import com.local.virpa.virpa.R
import com.local.virpa.virpa.api.VirpaApi
import com.local.virpa.virpa.enum.OpenGallery
import com.local.virpa.virpa.enum.Table
import com.local.virpa.virpa.enum.VirpaDB
import com.local.virpa.virpa.event.ShowSnackBar
import com.local.virpa.virpa.local_db.DatabaseHandler
import com.local.virpa.virpa.model.ChangeProfile
import com.local.virpa.virpa.model.DeleteFiles
import com.local.virpa.virpa.model.SaveFiles
import com.local.virpa.virpa.model.UpdateUser
import com.local.virpa.virpa.presenter.EditInfoPresenterClass
import com.local.virpa.virpa.presenter.EditInfoView
import com.squareup.picasso.Picasso
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_edit_info.*
import java.io.File

@Suppress("DEPRECATED_IDENTITY_EQUALS")
class EditInfoActivity : AppCompatActivity(), EditInfoView , ActivityCompat.OnRequestPermissionsResultCallback{

    private val apiServer by lazy {
        VirpaApi.create(this)
    }
    val presenter = EditInfoPresenterClass(this, apiServer)
    var bitmapImage : Bitmap? = null
    var  pathID : String = ""
    var  pathProfile : String = ""
    private var compositeDisposable : CompositeDisposable = CompositeDisposable()
    private val REQUEST_WRITE_PERMISSION = 786
    var fileID : String? = null
    var isChangeProfile = false
    var isChangeID = false
    var database = DatabaseHandler(this)
    var tempFullname = ""
    var tempBackground = ""
    var tempNumber = ""
    var userId = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_info)
        title = "Edit Information"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        presenter.getFiles()
        setUserInfo()
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

            if (requestCode === OpenGallery.ID.getValue()) {
                bitmapImage = MediaStore.Images.Media.getBitmap(contentResolver, data?.data )
                this.pathID = getPath(this, data?.data!!)
                attachment.setImageBitmap(MediaStore.Images.Media.getBitmap(contentResolver, data.data))
                isChangeID = true
            }
            else if (requestCode === OpenGallery.PROFILE.getValue()) {
                bitmapImage = MediaStore.Images.Media.getBitmap(contentResolver, data?.data )
                this.pathProfile = getPath(this, data?.data!!)
                profilePicture.setImageBitmap(MediaStore.Images.Media.getBitmap(contentResolver, data.data))
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
                if(tempFullname != editName.text.toString() || tempNumber != editNumber.text.toString() || tempBackground != editSummary.text.toString()) {
                    saveUserInfo()
                }
            }
        }
        return true
    }

    override fun successSaveFiles(data : SaveFiles.Result) {
        ShowSnackBar.present("ID Save" , this)
    }

    override fun successGetFiles(data: SaveFiles.Result) {
        var background : MutableList<SaveFiles.Files> = ArrayList()
        var id : MutableList<SaveFiles.Files> = ArrayList()
        var profile : MutableList<SaveFiles.Files> = ArrayList()
        data.data.files.forEach { item ->
            when(item.type) {
                1 -> {
                    id.add(item)
                }
                2 -> {
                    background.add(item)
                }
                3 -> {
                    profile.add(item)
                }
            }
        }
        fileID = id[0].id
        if (id[0].extension != "") {
            Glide.with(this)
                    .load(id[0].filePath)
                    .into(attachment)
        }
    }

    override fun sucessDeleteFile(data: DeleteFiles.Result) {
        fileID = null
        attachment.setImageResource(R.drawable.ic_default_image)
    }

    override fun successProfile(data: ChangeProfile.Result) {
        database.updateData(VirpaDB.USER_INFO.getValue(), Table.UserInfo.FILE_PATH.getValue(), data.data.files[0].filePath)
        ShowSnackBar.present("Profile Save" , this)
    }
    override fun successUpdateUser(data: UpdateUser.Result) {
        database.updateData(VirpaDB.USER_INFO.getValue(), Table.UserInfo.FULLNAME.getValue(), data.data.detail.fullname)
        database.updateData(VirpaDB.USER_INFO.getValue(), Table.UserInfo.BACKGROUND_SUMMARY.getValue(), data.data.detail.backgroundSummary)
        database.updateData(VirpaDB.USER_INFO.getValue(), Table.UserInfo.MOBILE_NUMBER.getValue(), data.data.detail.mobileNumber)
        ShowSnackBar.present("User Information Save" , this)
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
    private fun saveUserInfo() {
        var data = UpdateUser.Post(
                userId,
                editName.text.toString(),
                editNumber.text.toString(),
                editSummary.text.toString()
        )
        presenter.updateUserInfo(data)
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
    private fun setUserInfo() {
        var result = database.readSignResult()
        if(result[0].user.profilePicture?.filePath != "") {
            Glide.with(this)
                    .load(result[0].user.profilePicture?.filePath)
                    .into(profilePicture)
        }
        tempFullname = result[0].user.detail.fullname
        tempBackground = result[0].user.detail.backgroundSummary!!
        tempNumber = result[0].user.detail.mobileNumber
        userId = result[0].user.detail.id
        editName.setText(tempFullname)
        editNumber.setText(tempNumber)
        editSummary.setText(tempBackground)
    }
}
