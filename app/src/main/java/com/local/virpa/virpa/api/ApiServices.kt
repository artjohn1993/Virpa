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

    @Headers("content-type: application/json")
    @POST("files")
    fun saveFiles(@Body files : SaveFiles.Post) : Observable<SaveFiles.Result>


    @Headers("Content-Type: application/json")
    @POST("skills")
    fun saveMySkills(@Body client : SaveMySkills.Post) : Observable<MySkills.Result>

    @Headers("Content-Type: application/json")
    @POST("files/delete")
    fun deleteFiles(@Body client : DeleteFiles.Post) : Observable<DeleteFiles.Result>

    @Headers("Content-Type: application/json")
    @POST("followers")
    fun follow(@Body client : Follow.Post) : Observable<Follow.Result>

    @Headers("Content-Type: application/json")
    @POST("followers/unfollow")
    fun unFollow(@Body client : Follow.Post) : Observable<Follow.Result>

    @Headers("Content-Type: application/json")
    @POST("location")
    fun pinLocation(@Body client : Location.Post) : Observable<Location.Result>

    @Headers("Content-Type: application/json")
    @POST("user/profilePicture/change")
    fun changeProfile(@Body client : ChangeProfile.Post) : Observable<ChangeProfile.Result>

    @Headers("Content-Type: application/json")
    @POST("user/update")
    fun updateUserInfo(@Body client : UpdateUser.Post) : Observable<UpdateUser.Result>

    @Headers("Content-Type: application/json")
    @POST("bid")
    fun saveBid(@Body client : SaveBidder.Post) : Observable<SaveBidder.Result>

    //=========================================================

    @Headers("api-version: 1.0")
    @GET("feeds")
    fun getMyFeed() : Observable<Feed.Result>

    @Headers("api-version: 1.0")
    @GET("bid/{feedId}")
    fun getBidders(@Path( value = "feedId", encoded = true) feedId : String) : Observable<GetBidder.Result>

    @Headers("api-version: 1.0")
    @GET("bid/byId?")
    fun getBidderById(@Query("feedId") feed_Id : String,
                      @Query("bidderId") bidder_Id : String) : Observable<GetBidderById.Result>

    @Headers("api-version: 1.0")
    @GET("feeds/wall/{user_id}")
    fun getFeedByUser(@Path(value = "user_id", encoded = true) userId : String) : Observable<FeedByUser.Result>

    @Headers("api-version: 1.0")
    @GET("skills/list")
    fun getSkills() : Observable<Skills.Result>

    @Headers("api-version: 1.0")
    @GET("skills")
    fun getMySkills() : Observable<MySkills.Result>

    @Headers("content-type: application/json")
    @GET("files")
    fun getFiles() : Observable<SaveFiles.Result>

    @Headers("content-type: application/json")
    @GET("user/list")
    fun getUserList() : Observable<UserList.Result>

    @Headers("content-type: application/json")
    @GET("followed")
    fun getFollowed() : Observable<GetFollow.ResultFollowed>

    @Headers("content-type: application/json")
    @GET("followers")
    fun getFollower() : Observable<GetFollow.ResultFollower>
}