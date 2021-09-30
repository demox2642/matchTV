package com.skilbox.matchtv.network

import com.skilbox.matchtv.MatchInfo
import com.skilbox.matchtv.MatchLinc
import retrofit2.http.*

interface MatchApi {

    @Headers("Content-Type: application/json")
    @POST("test/video-urls")
    suspend fun getMatchLinc(@Body params: MutableMap<String, Int>): List<MatchLinc>

    @POST("test/data")
    suspend fun getMatchInfo(@Body params: ParamBody): MatchInfo
}
