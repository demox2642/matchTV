package com.skilbox.matchtv.network

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

object NetworkRetrofit {

    private val okHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        .build()

    private val moshiBuilder = Moshi.Builder()
        .add(CustomDateAdapter())

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshiBuilder.build()))
        .baseUrl("https://api.instat.tv/")
        .client(okHttpClient)
        .build()

    val api: MatchApi
        get() = retrofit.create()
}
