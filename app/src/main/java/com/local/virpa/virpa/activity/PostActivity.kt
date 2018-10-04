package com.local.virpa.virpa.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.developers.imagezipper.ImageZipper
import com.local.virpa.virpa.R
import com.local.virpa.virpa.api.VirpaApi
import com.local.virpa.virpa.dialog.Loading
import com.local.virpa.virpa.event.ShowSnackBar
import com.local.virpa.virpa.model.ForgetPass
import com.local.virpa.virpa.model.SaveFeed
import com.local.virpa.virpa.presenter.PostPresenterClass
import com.local.virpa.virpa.presenter.PostView
import kotlinx.android.synthetic.main.activity_post.*
import kotlinx.android.synthetic.main.layout_visited_user.*
import org.jetbrains.anko.startActivity
import java.io.File

class PostActivity : AppCompatActivity(), PostView {

    var path : String = ""
    var permission = android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    var EXTERNAL_STORAGE_PERMISSION = 1
    var loading = Loading(this)
    var type : Int = 0
    private val apiServer by lazy {
        VirpaApi.create(this)
    }
    val presenter = PostPresenterClass(this, apiServer)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        title = "Create Post"
        image?.setOnClickListener {
            var cameraIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(cameraIntent, 1000)
        }

        postType.setOnCheckedChangeListener { radioGroup, i ->
            when(i) {
                R.id.lf -> {
                    type = 0
                    priceWrapper.visibility = View.VISIBLE
                    postBudget.setText("")
                }
                R.id.announce -> {
                    type = 1
                    priceWrapper.visibility = View.GONE
                    postBudget.setText("0.0")
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode === Activity.RESULT_OK) {
            if (requestCode === 1000) {
                val returnUri = data?.data
                path = getPath(this , data?.data!!)
                postLocalImage?.setImageBitmap(MediaStore.Images.Media.getBitmap(this.contentResolver, returnUri))
                postLocalImage?.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            android.R.id.home -> {
                backToHome()
            }
            R.id.post -> {
                if (type == 0) {
                    if (postBody.text.toString() != "" && postBudget.text.toString() != "") {
                        saveFeed()
                    } else {
                        ShowSnackBar.present("Incomplete information", this)
                    }
                }
                else {
                    saveFeed()
                }
            }
        }
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        backToHome()
    }

    fun saveFeed() {
        if(checkStoragePermission()) {
            loading.show()
            var file = File(path)
            if(path != "") {
                var base64 = ImageZipper.getBase64forImage(file).toString()
                val saveFeed = SaveFeed.PostCoverPhoto(file.name, base64.toString())
                var data = SaveFeed.Post("0",type, postBody.text.toString(), postBudget.text.toString().toDouble(),3, saveFeed)
                presenter.saveMyFeed(data)
            }
            else {
                var data = SaveFeed.Post("0",type, postBody.text.toString(), postBudget.text.toString().toDouble(),3, null)
                presenter.saveMyFeed(data)
            }

        }
        else {
            ShowSnackBar.present("Storage permission denied", this)
        }
    }

    fun checkStoragePermission() : Boolean {
        if(Build.VERSION.SDK_INT >= 23) {
            if( checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
                return true
            }
            else {
                ActivityCompat.requestPermissions(this, arrayOf(permission), EXTERNAL_STORAGE_PERMISSION)
                return false
            }
        }
        else {
            return true
        }
    }
    fun backToHome() {
        startActivity<HomeActivity>()
        finish()
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

    override fun saveFeedResponse(data: SaveFeed.Result) {
        backToHome()
    }

    override fun saveFeedError(data: String) {

    }
}
