package com.thecodeproject.`in`.safezone.models

/*data class EarthquakeResponse(
    val features: List<Feature>
)

data class Feature(
    val properties: Properties
)

data class Properties(
    val mag: Double,
    val place: String,
    val time: Long
)*/

// Data class for the entire response
data class EarthquakeResponse(
    val type: String,
    val features: List<Feature>
)

// Data class for each feature
data class Feature(
    val type: String,
    val bbox: List<Double>,
    val geometry: Geometry,
    val properties: Properties
)

// Data class for geometry details
data class Geometry(
    val type: String,
    val coordinates: List<Double>
)

// Data class for event properties
data class Properties(
    val eventtype: String,
    val eventid: Int,
    val episodeid: Int,
    val eventname: String?,
    val glide: String?,
    val name: String,
    val description: String,
    val htmldescription: String,
    val icon: String,
    val iconoverall: String,
    val url: Url,
    val alertlevel: String,
    val alertscore: Double,
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
    val sourceid: String?,
    val polygonlabel: String,
    val `class`: String,
    val affectedcountries: List<AffectedCountry>,
    val severitydata: SeverityData
)

// Data class for URLs in properties
data class Url(
    val geometry: String,
    val report: String,
    val details: String
)

// Data class for affected countries
data class AffectedCountry(
    val iso3: String,
    val countryname: String
)

// Data class for severity data
data class SeverityData(
    val severity: Double,
    val severitytext: String,
    val severityunit: String
)
