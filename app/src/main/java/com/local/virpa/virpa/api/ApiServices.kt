package com.local.virpa.virpa.api

import com.local.virpa.virpa.model.CreateUser
import com.local.virpa.virpa.model.ForgetPass
import com.local.virpa.virpa.model.SignIn
import com.local.virpa.virpa.model.SignOut
import com.squareup.moshi.Json
import io.reactivex.Observable
import org.json.JSONObject
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import java.util.*


interface ApiServices {

    @Headers("content-type: application/json")
    @POST("user/create")
    fun createUser(@Body client : CreateUser.Post) : Observable<CreateUser.Result>

    @Headers("content-type: application/json")
    @POST("auth/sign-in")
    fun login(@Body client : SignIn.Request) : Observable<SignIn.Result>

    @Headers("content-type: application/json")
    @POST("auth/sign-out")
    fun signout(@Body client : SignOut.POST) : Observable<SignOut.Result>

    @Headers("content-type: application/json")
    @POST("user/forgot-password")
    fun forgetPassword(@Body client : ForgetPass.Post) : Observable<ForgetPass.Result>
}