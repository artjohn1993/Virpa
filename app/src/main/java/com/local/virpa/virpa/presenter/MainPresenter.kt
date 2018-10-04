package com.local.virpa.virpa.presenter

import com.local.virpa.virpa.api.ApiServices
import com.local.virpa.virpa.enum.publicToken
import com.local.virpa.virpa.model.CreateUser
import com.local.virpa.virpa.model.ForgetPass
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
                            if (result.message.isEmpty()) {
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
                                publicToken = result.data.authorization.sessionToken.token
                                view.loginSuccess(result)
                            }
                            else
                            {
                                view.loginFailed(result.message[0])
                            }
                        },{
                            error ->
                            println(error.toString())
                            view.loginFailed("Invalid email!")
                        })
        )
    }
    override fun forgetPass(data: ForgetPass.Post) {
        compositeDisposable.add(
                api.forgetPassword(data)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.newThread())
                        .subscribe({ result ->
                            view.forgetPassSuccess(result)
                        },{
                            error ->
                           view.forgetPassFailed(error.message.toString())
                        })
        )
    }
}

interface MainView {
    fun createSuccess(data : CreateUser.Result)
    fun createFailed(data : String)
    fun loginSuccess(data : SignIn.Result)
    fun loginFailed(data : String)
    fun forgetPassSuccess(data : ForgetPass.Result)
    fun forgetPassFailed(data : String)
}
interface MainPresenter {
    fun createUser(data : CreateUser.Post)
    fun login(data : SignIn.Request)
    fun forgetPass(data : ForgetPass.Post)
}
