package com.local.virpa.virpa.api

import android.content.Context
import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

class CustomHttp {
    companion object {
        fun createOkhttp() : OkHttpClient {
            val okhttp = OkHttpClient.Builder()

            val interceptor = Interceptor { chain ->
                var request: Request? = null

                request = chain?.request()?.newBuilder()
                        ?.addHeader("Content-Type",
                                "application/json")
                        ?.addHeader("api-version",
                                "1.0")
                        ?.build()

                chain.proceed(request)
            }
            okhttp.networkInterceptors().add(interceptor)
            okhttp.protocols(Arrays.asList(Protocol.HTTP_1_1))
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
                    .baseUrl("http://13.229.223.116/api/")
                    .client(CustomHttp.createOkhttp())
                    .build()
            return retrofit.create(ApiServices::class.java)
        }
    }
}