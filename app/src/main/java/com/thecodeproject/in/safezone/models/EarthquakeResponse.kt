package com.thecodeproject.`in`.safezone.models

data class EarthquakeResponse(
    val features: List<Feature>
)

data class Feature(
    val properties: Properties
)

data class Properties(
    val mag: Double,
    val place: String,
    val time: Long
)
