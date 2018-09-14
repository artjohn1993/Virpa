package com.local.virpa.virpa.presenter

import com.local.virpa.virpa.api.ApiServices
import com.local.virpa.virpa.model.SaveFeed
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PostPresenterClass(val view : PostView ,var api : ApiServices) : PostPresenter {

    private var compositeDisposable : CompositeDisposable = CompositeDisposable()

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

interface PostPresenter {
    fun saveMyFeed(data : SaveFeed.Post)
}
interface PostView {
    fun saveFeedResponse(data : SaveFeed.Result)
    fun saveFeedError(data : String)
}