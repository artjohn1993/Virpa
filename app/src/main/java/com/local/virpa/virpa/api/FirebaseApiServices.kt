package com.local.virpa.virpa.api

import com.local.virpa.virpa.model.FSend
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface FirebaseApiServices {
    @Headers("content-type: application/json")
    @POST("fcm/send")
    fun send(@Body client : FSend.Post) : Observable<FSend.Result>
}