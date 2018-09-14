package com.local.virpa.virpa.presenter

import com.local.virpa.virpa.api.ApiServices
import com.local.virpa.virpa.model.FeedByUser
import com.local.virpa.virpa.model.Follow
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class VisitedProfilePresenterClass(var view : VisitedProfileView ,var api : ApiServices) : VisitedProfilePresenter {


    private var compositeDisposable : CompositeDisposable = CompositeDisposable()

    override fun getUserFeed(data : String) {
        compositeDisposable.add(
                api.getFeedByUser(data)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe({ result ->
                            view.responseUserFeed(result)
                        },{
                            error ->

                        })
        )
    }

    override fun follow(data: Follow.Post) {
        compositeDisposable.add(
                api.follow(data)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe({ result ->
                            println(result.toString())
                        },{
                            error ->
                            println(error.toString())
                        })
        )
    }

    override fun unFollow(data: Follow.Post) {
        compositeDisposable.add(
                api.unFollow(data)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe({ result ->
                            println(result.toString())
                        },{
                            error ->
                            println(error.toString())
                        })
        )
    }
}
interface VisitedProfilePresenter {
    fun getUserFeed(data : String)
    fun follow(data : Follow.Post)
    fun unFollow(data : Follow.Post)
}
interface VisitedProfileView {
    fun responseUserFeed(data : FeedByUser.Result?)
}