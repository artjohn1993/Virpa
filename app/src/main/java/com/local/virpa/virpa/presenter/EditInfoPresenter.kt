package com.local.virpa.virpa.presenter

import com.local.virpa.virpa.api.ApiServices
import com.local.virpa.virpa.model.SaveFiles
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody

class EditInfoPresenterClass(var view : EditInfoView, var api : ApiServices) : EditInfoPresenter {
    private var compositeDisposable : CompositeDisposable = CompositeDisposable()

    override fun saveFiles(data : MultipartBody.Part) {
        compositeDisposable.add(
                api.saveFiles(data)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe({ result ->
                            println(result.toString())
                        },{
                            error ->
                            println(error.cause?.message.toString())
                        })
        )
    }

}
interface EditInfoPresenter {
    fun saveFiles(data : MultipartBody.Part)
}
interface EditInfoView {
    fun successSaveFiles(data : SaveFiles.Result)
}