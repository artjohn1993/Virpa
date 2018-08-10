package com.local.virpa.virpa.presenter

import com.local.virpa.virpa.api.ApiServices
import com.local.virpa.virpa.model.SignOut
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SettingsPresenterClass(val view : SettingsView,var api : ApiServices) : SettingsPresenter {

    private var compositeDisposable : CompositeDisposable = CompositeDisposable()
    override fun signOut(data: SignOut.POST) {
        compositeDisposable.add(
                api.signout(data)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe({ result ->
                            view.success(result)
                        },{
                            error ->
                            view.failed(error.message.toString())
                        })
        )
    }

}

interface SettingsView{
    fun success(data : SignOut.Result)
    fun failed(data : String)
}
interface SettingsPresenter {
    fun signOut(data : SignOut.POST)
}