package com.thecodeproject.`in`.safezone.models

data class WeatherResponse(
    val main: Main,
    val wind: Wind,
    val visibility: Int,
    val sys: Sys
)

data class Main(
    val temp: Double,
    val humidity: Int,
    val sea_level: Int
)

data class Wind(
    val speed: Double
)

data class Sys(
    val country: String
)