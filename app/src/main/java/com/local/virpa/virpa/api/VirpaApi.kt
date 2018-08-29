package com.local.virpa.virpa.api

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.local.virpa.virpa.BuildConfig
import com.local.virpa.virpa.enum.publicToken
import com.local.virpa.virpa.local_db.DatabaseHandler
import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

class CustomHttp {

    companion object {
        fun createOkhttp(context: Context) : OkHttpClient {
            val okhttp = OkHttpClient.Builder()
            val interceptor = Interceptor { chain ->
                var request: Request? = null
                var token : String  = ""

                if (publicToken != null) {
                    token = publicToken as String
                }
                if (BuildConfig.DEBUG) {
                    val sample = HttpLoggingInterceptor()
                    sample.level = HttpLoggingInterceptor.Level.BASIC
                    okhttp.addInterceptor(sample)
                }

                request = chain?.request()?.newBuilder()
                        ?.addHeader("Content-Type", "application/json")
                        ?.addHeader("api-version",
                                "1.0")
                        ?.addHeader("Authorization",
                                "Bearer $token")
                        ?.build()

                chain.proceed(request)
            }
            okhttp.networkInterceptors().add(interceptor)
            //okhttp.protocols(Arrays.asList(Protocol.HTTP_1_1))
            return okhttp.build()
        }
    }
}

class VirpaApi {
    companion object {
        fun create(context: Context) : ApiServices {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(MoshiConverterFactory.create())
                    .baseUrl("http://54.169.135.20/api/")
                    .client(CustomHttp.createOkhttp(context))
                    .build()
            return retrofit.create(ApiServices::class.java)
        }
    }
}