package com.thecodeproject.`in`.safezone.network

import com.thecodeproject.`in`.safezone.models.EarthquakeResponse
import com.thecodeproject.`in`.safezone.models.ForestFireResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GdacsApiService {

    //Earthquake
    @GET("events/geteventlist/SEARCH")
    fun getEarthquakeList(
        @Query("fromDate") fromDate: String,
        @Query("toDate") toDate: String,
        @Query("alertlevel") alertLevel: String,
        @Query("eventlist") eventList: String,
        @Query("country") country: String
    ): Call<EarthquakeResponse>

    @GET("events/geteventlist/SEARCH")
    fun getForestFires(
        @Query("fromDate") fromDate: String,
        @Query("toDate") toDate: String,
        @Query("alertlevel") alertLevel: String,
        @Query("eventlist") eventList: String,
        @Query("country") country: String
    ): Call<ForestFireResponse>

}