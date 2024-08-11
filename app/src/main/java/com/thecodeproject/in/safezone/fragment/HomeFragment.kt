package com.thecodeproject.`in`.safezone.fragment

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import com.thecodeproject.`in`.safezone.adapter.NewsAdapter
import com.thecodeproject.`in`.safezone.databinding.FragmentHomeBinding
import com.thecodeproject.`in`.safezone.retrofit.NewsRetrofitInstance
import com.thecodeproject.`in`.safezone.retrofit.WeatherRetrofitInstance
import com.thecodeproject.`in`.safezone.sharedPref.AuthSharedPref
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var authSharedPref: AuthSharedPref

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        authSharedPref = AuthSharedPref(requireContext())

        checkLocationPermissions()

        binding.rvNews.layoutManager = LinearLayoutManager(context)
        getNews()

        binding.btnCallSOS.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:1916")
            }
            context?.startActivity(intent)
        }


        return binding.root
    }

    private fun getWeatherInfo(latitude: Double, longitude: Double) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = WeatherRetrofitInstance.api.getCurrentWeather(
                    latitude = latitude,
                    longitude = longitude,
                    apiKey = "8b46167ec30c63b2c3f04a2f52959e3a"
                )
                binding.tvTemp.text = "${response.main.temp}\u00B0"
                binding.tvHumidityValue.text = response.main.humidity.toString()
                binding.tvVisibilityValue.text = response.visibility.toString()
                binding.tvSeaLevelValue.text = response.main.sea_level.toString()

            } catch (e: Exception) {
                Snackbar.make(binding.main, e.message.toString(), Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun getNews() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = NewsRetrofitInstance.api.getNewsArticles()
                if (response.isSuccessful && response.body() != null) {
                    val articles = response.body()!!.articles
                    binding.rvNews.adapter = NewsAdapter(requireContext(), articles)
                } else {
                    Snackbar.make(binding.main, "Failed to load news", Snackbar.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Snackbar.make(binding.main, e.message.toString(), Snackbar.LENGTH_SHORT).show()
                Log.e("News API Error", e.message.toString())
            }
        }
    }

    private fun checkLocationPermissions() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            getLocation()
        }
    }

    private fun getLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
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
                getWeatherInfo(it.latitude, it.longitude)
            }
        }
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

}