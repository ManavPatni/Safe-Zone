package com.thecodeproject.`in`.safezone.disasters

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.thecodeproject.`in`.safezone.R
import com.thecodeproject.`in`.safezone.TimelineDecoration
import com.thecodeproject.`in`.safezone.adapter.EarthquakeAdapter
import com.thecodeproject.`in`.safezone.databinding.ActivityEarthquakeBinding
import com.thecodeproject.`in`.safezone.models.EarthquakeResponse
import com.thecodeproject.`in`.safezone.network.EarthquakeApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class EarthquakeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEarthquakeBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var earthquakeApiService: EarthquakeApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEarthquakeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupApiService()
        setupRecyclerView()
        checkLocationPermissions()

    }

    private fun setupApiService() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://earthquake.usgs.gov/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        earthquakeApiService = retrofit.create(EarthquakeApiService::class.java)
    }

    private fun setupRecyclerView() {
        binding.earthquakeRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@EarthquakeActivity)
            addItemDecoration(TimelineDecoration())
        }
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
                fetchNearbyEarthquakes(it.latitude, it.longitude)
            }
        }
    }

    private fun fetchNearbyEarthquakes(latitude: Double, longitude: Double) {
        val format = "geojson"
        val endTime = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, -2)
        val startTime = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)
        val maxRadiusKm = 100.0

        earthquakeApiService.getEarthquakes(format, startTime, endTime, latitude, longitude, maxRadiusKm).enqueue(object :
            Callback<EarthquakeResponse> {
            override fun onResponse(call: Call<EarthquakeResponse>, response: Response<EarthquakeResponse>) {
                response.body()?.let {
                    binding.earthquakeRecyclerView.adapter = EarthquakeAdapter(it.features)
                }
            }

            override fun onFailure(call: Call<EarthquakeResponse>, t: Throwable) {
                t.printStackTrace()
                binding.earthquakeRecyclerView.visibility = View.GONE
                binding.tvNoEarthquakeFound.visibility = View.VISIBLE
            }
        })
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

}