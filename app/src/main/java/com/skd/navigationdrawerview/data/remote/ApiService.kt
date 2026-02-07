package com.skd.navigationdrawerview.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("v2/venues/search")
    suspend fun getMatches(
        @Query("ll") ll: String = "40.7484,-73.9857",
        @Query("oauth_token") token: String = "NPKYZ3WZ1VYMNAZ2FLX1WLECAWSMUVOQZOIDBN53F3LVZBPQ",
        @Query("v") version: String = "20180616"
    ): MatchResponse
}
