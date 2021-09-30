package com.skilbox.matchtv

import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class MatchInfo(
    val tournament: Tournament,
    val date: Date,
    val team1: Team,
    val team2: Team,
    val stream_status: String?
)
@JsonClass(generateAdapter = true)
data class Tournament(
    val id: Int,
    val name_eng: String,
    val name_rus: String
)
@JsonClass(generateAdapter = true)
data class Team(
    val id: Int,
    val name_eng: String,
    val name_rus: String,
    val score: Int
)
@JsonClass(generateAdapter = true)
data class MatchLinc(
    val name: String,
    val match_id: String,
    val period: String,
    val server_id: Int,
    val quality: String,
    val folder: String,
    val video_type: String,
    val abc: String,
    val start_ms: String,
    val checksum: String,
    val size: String,
    val abc_type: String,
    val duration: String,
    val fps: String,
    val url_root: String,
    val url: String
)
