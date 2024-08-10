package com.thecodeproject.`in`.safezone.network

import com.thecodeproject.`in`.safezone.models.EmergencyResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface EmergencyApiService {
    @GET("country/{code}")
    fun getEmergencyNumbers(@Path("code") countryCode: String): Call<EmergencyResponse>
}