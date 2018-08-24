package com.local.virpa.virpa.api

import com.local.virpa.virpa.model.*
import com.squareup.moshi.Json
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.http.*
import java.io.File
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
    @POST("auth/generate-token")
    fun refreshToken(@Body client : TokenRefresh.Post) : Observable<TokenRefresh.Result>

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @Multipart
    @POST("feeds")
    fun saveMyFeed(@Part("feedId") feedId : String,
                   @Part("type") type : String,
                   @Part("body") body : String,
                   @Part("budget") budget : String,
                   @Part("expiredOn") expiredOn : String,
                   @Part("coverPhoto") coverPhoto : File?) : Observable<SaveFeed.Result>

    @Headers("api-version: 1.0")
    @GET("feeds")
    fun getMyFeed() : Observable<Feed.Result>
}