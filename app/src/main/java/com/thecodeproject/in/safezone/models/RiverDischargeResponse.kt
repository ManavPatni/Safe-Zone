package com.thecodeproject.`in`.safezone.models

data class RiverDischargeResponse(
    val daily: DailyData
)

data class DailyData(
    val time: List<String>,
    val river_discharge_max: List<Double>
)
