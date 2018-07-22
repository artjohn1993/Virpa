package com.local.virpa.virpa.api

import com.local.virpa.virpa.model.CreateUser
import com.local.virpa.virpa.model.SignIn
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
}