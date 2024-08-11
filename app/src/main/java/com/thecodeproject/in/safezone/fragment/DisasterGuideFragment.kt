package com.thecodeproject.`in`.safezone.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.thecodeproject.`in`.safezone.databinding.FragmentDisasterGuideBinding


class DisasterGuideFragment : Fragment() {

    private lateinit var binding: FragmentDisasterGuideBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDisasterGuideBinding.inflate(layoutInflater)



        return binding.root
    }


}