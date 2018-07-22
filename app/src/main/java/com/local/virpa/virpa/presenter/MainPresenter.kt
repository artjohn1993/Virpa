package com.local.virpa.virpa.presenter

import com.local.virpa.virpa.api.ApiServices
import com.local.virpa.virpa.model.CreateUser
import com.local.virpa.virpa.model.SignIn
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.Android

class MainPresenterClass(var view : MainView, var api : ApiServices) : MainPresenter {

    private var compositeDisposable : CompositeDisposable = CompositeDisposable()

    override fun createUser(data : CreateUser.Post) {
        compositeDisposable.add(
                api.createUser(data)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe({ result ->
                            if (result.succeed) {
                                view.createSuccess(result)
                            }
                        },{
                            error ->
                            println(error)
                        })
        )
    }

    override fun login(data: SignIn.Request) {
        compositeDisposable.add(
                api.login(data)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe({ result ->
                            if (result.succeed) {
                                view.loginSuccess(result)
                            }
                        },{
                            error ->
                            println(error)
                        })
        )
    }
}

interface MainView {
    fun createSuccess(data : CreateUser.Result)
    fun loginSuccess(data : SignIn.Result)
}
interface MainPresenter {
    fun createUser(data : CreateUser.Post)
    fun login(data : SignIn.Request)
}
