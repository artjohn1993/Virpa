package com.local.virpa.virpa.presenter

import com.local.virpa.virpa.api.ApiServices
import com.local.virpa.virpa.model.GetBidder
import com.local.virpa.virpa.model.SaveBidder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class BidderPresenterClass(var view : BidderView, var api : ApiServices) : BidderPresenter {
    private var compositeDisposable : CompositeDisposable = CompositeDisposable()

    override fun getBidders(data : String) {
        compositeDisposable.add(
                api.getBidders(data)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe({ result ->
                            if (result.data != null) {
                                view.responseGetBidder(result)
                            } else {
                                view.responseGetBidderNull(result.message[0])
                            }
                        },{
                            error ->
                            println(error.cause?.message.toString())
                        })
        )
    }

    override fun saveBid(data : SaveBidder.Post) {
        compositeDisposable.add(
                api.saveBid(data)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe({ result ->
                            view.responseSaveBid(result)
                        },{
                            error ->
                            println(error.cause?.message.toString())
                        })
        )
    }

}

interface BidderPresenter {
    fun getBidders(data : String)
    fun saveBid(data : SaveBidder.Post)
}
interface BidderView {
    fun responseGetBidder(data : GetBidder.Result)
    fun responseGetBidderNull(data : String)
    fun responseSaveBid(data : SaveBidder.Result)
}