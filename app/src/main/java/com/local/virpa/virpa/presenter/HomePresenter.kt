package com.local.virpa.virpa.presenter

import com.local.virpa.virpa.api.ApiServices
import com.local.virpa.virpa.model.Feed
import com.local.virpa.virpa.model.SaveFeed
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class HomePresenterClass(val view : HomeView, val api : ApiServices) : HomePresenter {


    private var compositeDisposable : CompositeDisposable = CompositeDisposable()

    override fun getMyFeed() {
        compositeDisposable.add(
                api.getMyFeed()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe({ result ->
                            view.feedResponse(result)
                        },{
                            error ->
                            println(error.message.toString())
                            view.feedError(error.message.toString())
                        })
        )
    }

    override fun saveMyFeed(data : SaveFeed.Post) {
        compositeDisposable.add(
                api.saveMyFeed(data)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe({ result ->
                            view.saveFeedResponse(result)
                        },{
                            error ->
                            view.saveFeedError(error.toString())
                        })
        )

    }

}

interface HomeView {
    fun feedResponse(data : Feed.Result)
    fun feedError(data : String)
    fun saveFeedResponse(data : SaveFeed.Result)
    fun saveFeedError(data : String)
}

interface HomePresenter{
    fun getMyFeed()
    fun saveMyFeed(data : SaveFeed.Post)
}