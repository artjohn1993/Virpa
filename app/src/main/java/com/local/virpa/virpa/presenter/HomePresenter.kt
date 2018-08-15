package com.local.virpa.virpa.presenter

import com.local.virpa.virpa.api.ApiServices
import com.local.virpa.virpa.model.Feed
import com.local.virpa.virpa.model.SaveFeed
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.RequestBody

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
                            view.feedError(error.message.toString())
                        })
        )
    }

    override fun saveMyFeed(feedid: Int, type: Int, body: String, budget: Int, expiredOn: Int, coverPhoto: RequestBody) {

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
    fun saveMyFeed(feedid : Int,
                   type : Int,
                   body : String,
                   budget : Int,
                   expiredOn : Int,
                   coverPhoto : RequestBody)
}