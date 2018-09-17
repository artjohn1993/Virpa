package com.local.virpa.virpa.presenter

import com.local.virpa.virpa.api.ApiServices
import com.local.virpa.virpa.model.GetFollow
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FollowPresenterClass(var view : FollowView, var api : ApiServices) : FollowPresenter {

    private var compositeDisposable : CompositeDisposable = CompositeDisposable()

    override fun getFollowed() {
        compositeDisposable.add(
                api.getFollowed()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe({ result ->
                            view.responseFollowed(result)
                        },{
                            error ->
                            println(error.cause?.message.toString())
                        })
        )
    }

    override fun getFollower() {
        compositeDisposable.add(
                api.getFollower()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe({ result ->
                            view.responseFollower(result)
                        },{
                            error ->
                            println(error.cause?.message.toString())
                        })
        )
    }

}

interface FollowPresenter {
    fun getFollowed()
    fun getFollower()
}

interface FollowView {
    fun responseFollowed(data : GetFollow.ResultFollowed)
    fun responseFollower(data : GetFollow.ResultFollower)
}