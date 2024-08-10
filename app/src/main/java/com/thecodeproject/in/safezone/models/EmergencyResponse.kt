package com.thecodeproject.`in`.safezone.models

data class EmergencyResponse(
    val disclaimer: String,
    val error: String,
    val data: EmergencyData
)

data class EmergencyData(
    val country: Country,
    val ambulance: EmergencyService,
    val fire: EmergencyService,
    val police: EmergencyService,
    val dispatch: EmergencyService,
    val member_112: Boolean,
    val localOnly: Boolean,
    val nodata: Boolean
)

data class Country(
    val name: String,
    val ISOCode: String,
    val ISONumeric: String
)

data class EmergencyService(
    val all: List<String>?,
    val gsm: List<String>?,
    val fixed: List<String>?
)
