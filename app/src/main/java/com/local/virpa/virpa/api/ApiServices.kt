package com.local.virpa.virpa.api

import com.local.virpa.virpa.model.*
import com.squareup.moshi.Json
import io.reactivex.Observable
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.http.*
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

    @Headers("content-type: application/json")
    @GET("feeds/myfeeds")
    fun getMyFeed() : Observable<Feed.Result>

    @Headers("content-type: application/x-www-form-urlencoded")
    @POST("feeds/myfeed")
    fun saveMyFeed(@Field("feedId") feedId : Int,
                   @Field("type") type : Int,
                   @Field("body") body : String,
                   @Field("budget") budget : Int,
                   @Field("expiredOn") expiredOn : Int,
                   @Field("coverPhoto") coverPhoto : RequestBody) : Observable<SaveFeed.Result>
}