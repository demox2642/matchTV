package com.skilbox.matchtv.network.viewmodel

import com.skilbox.matchtv.MatchInfo
import com.skilbox.matchtv.MatchLinc
import com.skilbox.matchtv.network.NetworkRetrofit
import com.skilbox.matchtv.network.ParamBody

class MatchRepository {

    suspend fun getMatchLinc(params: MutableMap<String, Int>): List<MatchLinc> {
        return NetworkRetrofit.api.getMatchLinc(params)
    }

    suspend fun getMatchInfo(params: ParamBody): MatchInfo {

        return NetworkRetrofit.api.getMatchInfo(params)
    }
}
