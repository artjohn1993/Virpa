package com.local.virpa.virpa.presenter

import com.local.virpa.virpa.api.ApiServices
import com.local.virpa.virpa.model.GetBidderById
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ThreadPresenterClass(var view : ThreadView, var api : ApiServices) : ThreadPresenter {
    private var compositeDisposable : CompositeDisposable = CompositeDisposable()
    override fun getBidderById(bidderID : String, feedID : String) {
        compositeDisposable.add(
                api.getBidderById(feedID, bidderID)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe({ result ->
                            view.responseGetBidderById(result)
                        },{
                            error ->
                            print(error.message.toString())
                        })
        )
    }

}

interface ThreadPresenter {
    fun getBidderById(bidderID : String, feedID : String)
}
interface ThreadView {
    fun responseGetBidderById(data : GetBidderById.Result)
}