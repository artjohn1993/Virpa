package com.local.virpa.virpa.presenter

import com.local.virpa.virpa.api.ApiServices
import com.local.virpa.virpa.model.GetBidderById
import com.local.virpa.virpa.model.UpdateBid
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

    override fun negotiateBid(data: UpdateBid.Negotiate) {
        compositeDisposable.add(
                api.negotiate(data)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe({ result ->
                            view.responseUpdateBid(result)
                        },{
                            error ->
                            print(error.message.toString())
                        })
        )
    }

    override fun acceptBid(data: UpdateBid.Accept) {
        compositeDisposable.add(
                api.accept(data)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe({ result ->
                            view.responseUpdateBid(result)
                        },{
                            error ->
                            print(error.message.toString())
                        })
        )
    }

    override fun closeBid(data: UpdateBid.Close) {
        compositeDisposable.add(
                api.close(data)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe({ result ->
                            view.responseUpdateBid(result)
                        },{
                            error ->
                            print(error.message.toString())
                        })
        )
    }

}

interface ThreadPresenter {
    fun getBidderById(bidderID : String, feedID : String)
    fun negotiateBid(data : UpdateBid.Negotiate)
    fun acceptBid(data : UpdateBid.Accept)
    fun closeBid(data : UpdateBid.Close)
}
interface ThreadView {
    fun responseGetBidderById(data : GetBidderById.Result)
    fun responseUpdateBid(data : UpdateBid.Result)
}