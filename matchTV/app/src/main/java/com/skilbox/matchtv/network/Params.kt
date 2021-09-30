package com.skilbox.matchtv.network

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class Params(
    @SerializedName("_p_sport")
    val _p_sport: Int,
    @SerializedName("_p_match_id")
    val _p_match_id: Int
)


@JsonClass(generateAdapter = true)
data class ParamBody(
    @SerializedName("proc")
    val proc: String,
    @SerializedName("params")
    val params: Params

)

