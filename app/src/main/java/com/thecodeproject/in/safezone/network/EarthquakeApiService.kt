package com.thecodeproject.`in`.safezone.network

import com.thecodeproject.`in`.safezone.models.EarthquakeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface EarthquakeApiService {
    @GET("fdsnws/event/1/query")
    fun getEarthquakes(
        @Query("format") format: String,
        @Query("starttime") startTime: String,
        @Query("endtime") endTime: String,
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("maxradiuskm") maxRadiusKm: Double
    ): Call<EarthquakeResponse>
}