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

    @Headers("content-type: application/json")
    @POST("feeds")
    fun saveMyFeed(@Body client : SaveFeed.Post) : Observable<SaveFeed.Result>

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @Multipart
    @POST("files")
    fun saveFiles(@Part files : MultipartBody.Part) : Observable<SaveFiles.Result>

    @Headers("Content-Type: application/json")
    @POST("skills")
    fun saveMySkills(@Body client : SaveMySkills.Post) : Observable<MySkills.Result>

    //=========================================================

    @Headers("api-version: 1.0")
    @GET("feeds")
    fun getMyFeed() : Observable<Feed.Result>

    @Headers("api-version: 1.0")
    @GET("skills/list")
    fun getSkills() : Observable<Skills.Result>

    @Headers("api-version: 1.0")
    @GET("skills")
    fun getMySkills() : Observable<MySkills.Result>
}