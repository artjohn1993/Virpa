package com.local.virpa.virpa.presenter

import com.local.virpa.virpa.api.ApiServices
import com.local.virpa.virpa.model.TokenRefresh
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class TokenPresenterClass(var view : TokenView, var api : ApiServices) : TokenPresenter {

    private var compositeDisposable : CompositeDisposable = CompositeDisposable()

    override fun refreshToken(data : TokenRefresh.Post) {
        compositeDisposable.add(
                api.refreshToken(data)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe({ result ->
                           view.refreshSuccess(result)
                        },{
                            error ->
                            print(error.message.toString())
                        })
        )
    }
}

interface  TokenView {
    fun refreshSuccess(data : TokenRefresh.Result)
}
interface TokenPresenter {
    fun refreshToken(data : TokenRefresh.Post)
}