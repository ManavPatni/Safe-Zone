package com.thecodeproject.`in`.safezone.models

data class ForestFireResponse(
    val type: String,
    val features: List<FFRFeature>
)

data class FFRFeature(
    val type: String,
    val bbox: List<Double>,
    val geometry: FFRGeometry,
    val properties: FFRProperties
)

data class FFRGeometry(
    val type: String,
    val coordinates: List<Double>
)

data class FFRProperties(
    val eventtype: String,
    val eventid: Int,
    val episodeid: Int,
    val eventname: String,
    val glide: String,
    val name: String,
    val description: String,
    val htmldescription: String,
    val icon: String,
    val iconoverall: String,
    val url: Urls,
    val alertlevel: String,
    val alertscore: Int,
    val episodealertlevel: String,
    val episodealertscore: Double,
    val istemporary: String,
    val iscurrent: String,
    val country: String,
    val fromdate: String,
    val todate: String,
    val datemodified: String,
    val iso3: String,
    val source: String,
    val sourceid: String,
    val polygonlabel: String,
    val Class: String,
    val affectedcountries: List<FFRAffectedCountry>,
    val severitydata: FFRSeverityData
)

data class Urls(
    val geometry: String,
    val report: String,
    val details: String
)

data class FFRAffectedCountry(
    val iso3: String,
    val countryname: String
)

data class FFRSeverityData(
    val severity: Double,
    val severitytext: String,
    val severityunit: String
)
