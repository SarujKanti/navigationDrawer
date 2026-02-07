package com.skd.navigationdrawerview.data.remote

data class MatchResponse(
    val response: ResponseData
)

data class ResponseData(
    val venues: List<Venue>
)

data class Venue(
    val id: String,
    val name: String,
    val location: Location,
    var isSaved: Boolean = false
)
data class Location(
    val country: String
)
