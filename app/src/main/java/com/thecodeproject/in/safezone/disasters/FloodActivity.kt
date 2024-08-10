package com.thecodeproject.`in`.safezone.disasters

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.thecodeproject.`in`.safezone.adapter.EarthquakeAdapter
import com.thecodeproject.`in`.safezone.databinding.ActivityFloodBinding
import com.thecodeproject.`in`.safezone.models.EarthquakeResponse
import com.thecodeproject.`in`.safezone.models.RiverDischargeResponse
import com.thecodeproject.`in`.safezone.network.OpenMeteoApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class FloodActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFloodBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFloodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkLocationPermissions()

        binding.cvBack.setOnClickListener { finish() }
    }

    private fun fetchNearbyFloodInfo(latitude: Double, longitude: Double){
        // Initialize Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://flood-api.open-meteo.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(OpenMeteoApiService::class.java)

        val call = service.getRiverDischarge(latitude, longitude)
        call.enqueue(object : retrofit2.Callback<RiverDischargeResponse> {
            override fun onResponse(call: Call<RiverDischargeResponse>, response: retrofit2.Response<RiverDischargeResponse>) {
                val entries = response.body()?.daily?.river_discharge_max?.mapIndexed { index, value ->
                    Entry(index.toFloat(), value.toFloat())
                } ?: listOf()

                val dataSet = LineDataSet(entries, "River Discharge")
                dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER // Set the mode to cubic lines
                dataSet.setDrawFilled(true) // Optional: if you want the area under the line to be filled
                dataSet.setDrawCircles(false) // Optional: if you want to hide the circles at data points

                binding.chart.data = LineData(dataSet)
                binding.chart.description.isEnabled = false
                binding.chart.invalidate()
            }

            override fun onFailure(call: Call<RiverDischargeResponse>, t: Throwable) {
                // Handle failure
            }
        })
    }

    private fun checkLocationPermissions() {
        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
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
                fetchNearbyFloodInfo(it.latitude, it.longitude)
            }
        }
    }


    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

}