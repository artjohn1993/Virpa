package com.local.virpa.virpa.presenter

import com.local.virpa.virpa.api.ApiServices
import com.local.virpa.virpa.model.ChangeProfile
import com.local.virpa.virpa.model.DeleteFiles
import com.local.virpa.virpa.model.SaveFiles
import com.local.virpa.virpa.model.UpdateUser
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody

class EditInfoPresenterClass(var view : EditInfoView, var api : ApiServices) : EditInfoPresenter {

    private var compositeDisposable : CompositeDisposable = CompositeDisposable()

    override fun saveFiles(data : SaveFiles.Post) {
        compositeDisposable.add(
                api.saveFiles(data)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe({ result ->
                            view.successSaveFiles(result)
                        },{
                            error ->
                            println(error.cause?.message.toString())
                        })
        )
    }
    override fun changeProfile(data: ChangeProfile.Post) {
        compositeDisposable.add(
                api.changeProfile(data)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe({ result ->
                            view.successProfile(result)
                        },{
                            error ->
                            println(error.cause?.message.toString())
                        })
        )
    }

    override fun updateUserInfo(data: UpdateUser.Post) {
        compositeDisposable.add(
                api.updateUserInfo(data)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe({ result ->
                            view.successUpdateUser(result)
                        },{
                            error ->
                            println(error.cause?.message.toString())
                        })
        )
    }

    override fun getFiles() {
        compositeDisposable.add(
                api.getFiles()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe({ result ->
                            view.successGetFiles(result)
                        },{
                            error ->
                            println(error.cause?.message.toString())
                        })
        )
    }
    override fun deleteFile(data: DeleteFiles.Post) {
        compositeDisposable.add(
                api.deleteFiles(data)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe({ result ->
                            view.sucessDeleteFile(result)
                        },{
                            error ->
                            println(error.cause?.message.toString())
                        })
        )
    }

}
interface EditInfoPresenter {
    fun saveFiles(data : SaveFiles.Post)
    fun getFiles()
    fun deleteFile(data : DeleteFiles.Post)
    fun changeProfile(data : ChangeProfile.Post)
    fun updateUserInfo(data : UpdateUser.Post)
}
interface EditInfoView {
    fun successSaveFiles(data : SaveFiles.Result)
    fun successGetFiles(data : SaveFiles.Result)
    fun sucessDeleteFile(data : DeleteFiles.Result)
    fun successProfile(data : ChangeProfile.Result)
    fun successUpdateUser(data : UpdateUser.Result)
}