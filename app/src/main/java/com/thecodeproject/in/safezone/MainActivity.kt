package com.thecodeproject.`in`.safezone

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.thecodeproject.`in`.safezone.auth.SignInActivity
import com.thecodeproject.`in`.safezone.databinding.ActivityMainBinding
import com.thecodeproject.`in`.safezone.disasters.DisasterGuideActivity
import com.thecodeproject.`in`.safezone.fragment.AlertFragment
import com.thecodeproject.`in`.safezone.fragment.DisasterGuideFragment
import com.thecodeproject.`in`.safezone.fragment.HomeFragment
import com.thecodeproject.`in`.safezone.sharedPref.AuthSharedPref

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var authSharedPref: AuthSharedPref
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(HomeFragment())

        auth = FirebaseAuth.getInstance()
        authSharedPref = AuthSharedPref(this)
        val currentUser = auth.currentUser

        if (currentUser != null) {
            // Load profile details only if the user is signed in
            Glide.with(this).load(currentUser.photoUrl).into(binding.ivProfilePic)
            binding.tvUsername.text = authSharedPref.userName()
            binding.tvCountry.text = authSharedPref.country()
        } else {
            // Handle the case where no user is signed in
            Log.e("MainActivity", "No user is signed in.")
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }

        binding.bnv.setOnItemSelectedListener {
            when (it.toString()) {
                "Home" -> {
                    replaceFragment(HomeFragment())
                }
                "Disaster Guide" -> {
                    replaceFragment(DisasterGuideFragment())
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
