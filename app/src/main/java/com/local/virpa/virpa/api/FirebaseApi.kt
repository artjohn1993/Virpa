package com.local.virpa.virpa.api

import android.content.Context
import com.local.virpa.virpa.BuildConfig
import com.local.virpa.virpa.enum.publicFKey
import com.local.virpa.virpa.enum.publicToken
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class CustomFirebaseHttp {
    companion object {
        fun createOkhttp(context: Context) : OkHttpClient {
            val okhttp = OkHttpClient.Builder()
            val interceptor = Interceptor { chain ->
                var request: Request? = null
                var token : String  = ""
                if (publicFKey != null) {
                    token = publicFKey as String
                }

                if (BuildConfig.DEBUG) {
                    val sample = HttpLoggingInterceptor()
                    sample.level = HttpLoggingInterceptor.Level.BASIC
                    okhttp.addInterceptor(sample)
                }

                request = chain?.request()?.newBuilder()
                        ?.addHeader("Content-Type", "application/json")
                        ?.addHeader("Authorization",
                                "key=$token")
                        ?.build()

                chain.proceed(request)
            }
            okhttp.networkInterceptors().add(interceptor)
            //okhttp.protocols(Arrays.asList(Protocol.HTTP_1_1))
            return okhttp.build()
        }
    }
}

class FirebaseApi {
    companion object {
        fun create(context: Context) : FirebaseApiServices {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(MoshiConverterFactory.create())
                    .baseUrl("https://fcm.googleapis.com/")
                    .client(CustomFirebaseHttp.createOkhttp(context))
                    .build()
            return retrofit.create(FirebaseApiServices::class.java)
        }
    }
}