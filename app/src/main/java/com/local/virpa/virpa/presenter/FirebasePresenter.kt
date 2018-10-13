package com.local.virpa.virpa.presenter

import com.local.virpa.virpa.api.FirebaseApiServices
import com.local.virpa.virpa.model.FSend
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FirebasePresenterClass(val view : FirebaseView, val api : FirebaseApiServices) : FirebasePresenter{

    private var compositeDisposable : CompositeDisposable = CompositeDisposable()

    override fun send(data: FSend.Post) {
        compositeDisposable.add(
                api.send(data).
                        observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe({ result ->
                            view.SendResponse(result)
                        },{
                            error ->
                            println(error.cause?.message.toString())
                        })
        )
    }

}

interface FirebasePresenter {
    fun send(data : FSend.Post)
}
interface FirebaseView {
    fun SendResponse(data: FSend.Result)
}