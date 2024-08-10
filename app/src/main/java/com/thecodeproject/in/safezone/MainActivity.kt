package com.thecodeproject.`in`.safezone

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.thecodeproject.`in`.safezone.adapter.EarthquakeAdapter
import com.thecodeproject.`in`.safezone.databinding.ActivityMainBinding
import com.thecodeproject.`in`.safezone.fragment.AlertFragment
import com.thecodeproject.`in`.safezone.fragment.HomeFragment
import com.thecodeproject.`in`.safezone.models.EarthquakeResponse
import com.thecodeproject.`in`.safezone.network.EarthquakeApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(HomeFragment())

        binding.bnv.setOnItemSelectedListener {
            when (it.toString()) {
                "Home" -> {
                    replaceFragment(HomeFragment())
                }
                "Alert" -> {
                    replaceFragment(AlertFragment())
                }
            }
            return@setOnItemSelectedListener true
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }

}
