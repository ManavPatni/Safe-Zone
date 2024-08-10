package com.thecodeproject.`in`.safezone.network

import com.thecodeproject.`in`.safezone.models.RiverDischargeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenMeteoApiService {
    @GET("flood")
    fun getRiverDischarge(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("daily") daily: String = "river_discharge_max",
        @Query("past_days") pastDays: Int = 3,
        @Query("forecast_days") forecastDays: Int = 7
    ): Call<RiverDischargeResponse>

}