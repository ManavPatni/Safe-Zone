package com.thecodeproject.`in`.safezone.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.thecodeproject.`in`.safezone.databinding.FragmentDisasterGuideBinding
import com.thecodeproject.`in`.safezone.disasters.DisasterGuideActivity


class DisasterGuideFragment : Fragment() {

    private lateinit var binding: FragmentDisasterGuideBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDisasterGuideBinding.inflate(layoutInflater)

        //wilde fire
        binding.cvWildeFire.setOnClickListener {
           val intent = Intent(context,DisasterGuideActivity::class.java)
           intent.putExtra("url","https://www.ready.gov/kids/games/data/dm-english/wildfire.html")
            startActivity(intent)
        }

        //Tornado
        binding.cvTornado.setOnClickListener {
           val intent = Intent(context,DisasterGuideActivity::class.java)
           intent.putExtra("url","https://www.ready.gov/kids/games/data/dm-english/tornado.html")
            startActivity(intent)
        }

        //Hurricane
        binding.cvHurricane.setOnClickListener {
           val intent = Intent(context,DisasterGuideActivity::class.java)
           intent.putExtra("url","https://www.ready.gov/kids/games/data/dm-english/hurricane-blackout.html")
            startActivity(intent)
        }

        //Winter Storm
        binding.cvWinterStorm.setOnClickListener {
           val intent = Intent(context,DisasterGuideActivity::class.java)
           intent.putExtra("url","https://www.ready.gov/kids/games/data/dm-english/cold.html")
            startActivity(intent)
        }

        //Tsunami
        binding.cvTsunami.setOnClickListener {
            val intent = Intent(context,DisasterGuideActivity::class.java)
            intent.putExtra("url","https://www.ready.gov/kids/games/data/dm-english/tsunami-earthquake.html")
            startActivity(intent)
        }

        //ThunderStorm
        binding.cvThunderStorm.setOnClickListener {
            val intent = Intent(context,DisasterGuideActivity::class.java)
            intent.putExtra("url","https://www.ready.gov/kids/games/data/dm-english/thunderstorm-Lightning.html")
            startActivity(intent)
        }

        return binding.root
    }


}