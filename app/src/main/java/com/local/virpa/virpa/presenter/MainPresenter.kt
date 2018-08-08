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
        var returnData : CreateUser.Result? = null
        compositeDisposable.add(
                api.createUser(data)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe({ result ->
                            if (result.message.isEmpty()) {
                                returnData = result
                                view.createSuccess(result)
                            }
                            else {
                                view.createFailed(result.message[0])
                            }
                        },{
                            error ->
                                view.createFailed(error.message.toString())
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
                            else
                            {
                                view.loginFailed(result.message[0])
                            }
                        },{
                            error ->
                            view.loginFailed("Invalid email!")
                        })
        )
    }
}

interface MainView {
    fun createSuccess(data : CreateUser.Result)
    fun createFailed(data : String)
    fun loginSuccess(data : SignIn.Result)
    fun loginFailed(data : String)
}
interface MainPresenter {
    fun createUser(data : CreateUser.Post)
    fun login(data : SignIn.Request)
}
