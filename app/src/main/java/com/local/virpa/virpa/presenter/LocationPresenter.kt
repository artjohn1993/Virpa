package com.local.virpa.virpa.presenter

import com.local.virpa.virpa.api.ApiServices
import com.local.virpa.virpa.model.Location
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LocationPresenterClass(var view : LocationView, val api : ApiServices) : LocationPresenter {

    private var compositeDisposable : CompositeDisposable = CompositeDisposable()

    override fun pinLocation(data: Location.Post) {
        compositeDisposable.add(
                api.pinLocation(data)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe({ result ->
                            view.responsePinLocation(result)
                        },{
                            error ->

                        })
        )
    }
}

interface LocationPresenter {
    fun pinLocation(data : Location.Post)
}

interface LocationView {
    fun responsePinLocation(data : Location.Result)
}