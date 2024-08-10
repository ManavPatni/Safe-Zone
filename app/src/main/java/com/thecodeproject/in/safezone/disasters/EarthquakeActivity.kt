package com.thecodeproject.`in`.safezone.disasters

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.thecodeproject.`in`.safezone.TimelineDecoration
import com.thecodeproject.`in`.safezone.adapter.EarthquakeAdapter
import com.thecodeproject.`in`.safezone.databinding.ActivityEarthquakeBinding
import com.thecodeproject.`in`.safezone.models.EarthquakeResponse
import com.thecodeproject.`in`.safezone.network.GdacsApiService
import com.thecodeproject.`in`.safezone.sharedPref.AuthSharedPref
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.math.pow

class EarthquakeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEarthquakeBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    //private lateinit var earthquakeApiService: EarthquakeApiService

    private lateinit var authSharedPref: AuthSharedPref

    private var WARNING_DISPLAY_STATUS = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEarthquakeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authSharedPref = AuthSharedPref(this)

        //setupApiService()
        setupRecyclerView()
        checkLocationPermissions()

        binding.cvBack.setOnClickListener { finish() }

        binding.cvBack.setOnLongClickListener {
            if (WARNING_DISPLAY_STATUS == 0) {
                showAlertDialog(
                    "Red Alert",
                    "An earthquake is very close to you!"
                )
                WARNING_DISPLAY_STATUS = 1
            } else {
                showAlertDialog(
                    "Orange Alert",
                    "An earthquake occurred recently within 200 km of your location."
                )
                WARNING_DISPLAY_STATUS = 0
            }
            true // Return true to indicate the long click event was handled
        }


    }

    private fun setupApiService() {
        /*val retrofit = Retrofit.Builder()
            .baseUrl("https://earthquake.usgs.gov/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        earthquakeApiService = retrofit.create(EarthquakeApiService::class.java)*/
    }

    private fun setupRecyclerView() {
        binding.earthquakeRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@EarthquakeActivity)
            addItemDecoration(TimelineDecoration())
        }
    }

    private fun checkLocationPermissions() {
        if (ActivityCompat.checkSelfPermission(
                this,
                ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            getLocation()
        }
    }


    private fun getLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let {
                fetchNearbyEarthquakes(it.latitude, it.longitude)
            }
        }
    }

    private fun fetchNearbyEarthquakes(latitude: Double, longitude: Double) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.gdacs.org/gdacsapi/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(GdacsApiService::class.java)

        val toDate = LocalDate.now()
        val fromDate = toDate.minusMonths(5)
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val fromDateString = fromDate.format(dateFormatter)
        val toDateString = toDate.format(dateFormatter)

        val call = apiService.getEarthquakeList(
            fromDate = fromDateString,
            toDate = toDateString,
            alertLevel = "green;orange;red",
            eventList = "EQ",
            country = authSharedPref.country().toString()
        )

        call.enqueue(object : Callback<EarthquakeResponse> {
            override fun onResponse(
                call: Call<EarthquakeResponse>,
                response: Response<EarthquakeResponse>
            ) {
                if (response.isSuccessful) {
                    val eventResponse = response.body()
                    binding.earthquakeRecyclerView.adapter = EarthquakeAdapter(this@EarthquakeActivity,eventResponse!!.features)
                    binding.earthquakeRecyclerView.layoutManager = LinearLayoutManager(this@EarthquakeActivity)
                    eventResponse?.features?.forEach { feature ->
                        val earthquakeLat = feature.geometry.coordinates[1]
                        val earthquakeLon = feature.geometry.coordinates[0]
                        val isCurrent = feature.properties.iscurrent == "true"
                        val earthquakeDate = LocalDate.parse(feature.properties.fromdate.substringBefore("T"))

                        // Calculate distance
                        val distance =
                            calculateDistance(latitude, longitude, earthquakeLat, earthquakeLon)

                        // Check date and distance for alerts
                        val daysDifference = ChronoUnit.DAYS.between(earthquakeDate, toDate)
                        if (isCurrent && distance <= 200 && WARNING_DISPLAY_STATUS == 0) {
                            when {
                                daysDifference <= 7 -> showAlertDialog(
                                    "Red Warning",
                                    "An earthquake is very close to you!"
                                )

                                daysDifference <= 14 -> showAlertDialog(
                                    "Orange Alert",
                                    "An earthquake occurred recently within 200 km of your location."
                                )
                            }
                        }
                    }
                } else {
                    println("Request failed with code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<EarthquakeResponse>, t: Throwable) {
                println("Network request failed: ${t.message}")
            }
        })

        /*val format = "geojson"
        val endTime = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, -2)
        val startTime = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)
        val maxRadiusKm = 100.0

        earthquakeApiService.getEarthquakes(
            format,
            startTime,
            endTime,
            latitude,
            longitude,
            maxRadiusKm
        ).enqueue(object :
            Callback<EarthquakeResponse> {
            override fun onResponse(
                call: Call<EarthquakeResponse>,
                response: Response<EarthquakeResponse>
            ) {
                response.body()?.let {
                    binding.earthquakeRecyclerView.adapter = EarthquakeAdapter(it.features)
                }
            }

            override fun onFailure(call: Call<EarthquakeResponse>, t: Throwable) {
                t.printStackTrace()
                binding.earthquakeRecyclerView.visibility = View.GONE
                binding.tvNoEarthquakeFound.visibility = View.VISIBLE
            }
        })*/
    }

    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val earthRadius = 6371 // Radius of the Earth in kilometers
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = Math.sin(dLat / 2).pow(2) + Math.cos(Math.toRadians(lat1)) * Math.cos(
            Math.toRadians(lat2)
        ) * Math.sin(dLon / 2).pow(2)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
        return earthRadius * c
    }

    private fun showAlertDialog(title: String, message: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                WARNING_DISPLAY_STATUS = 1
            }
            .show()
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

}