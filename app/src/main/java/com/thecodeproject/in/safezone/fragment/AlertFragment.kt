package com.thecodeproject.`in`.safezone.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.thecodeproject.`in`.safezone.databinding.FragmentAlertBinding

class AlertFragment : Fragment() {

    //view binding
    private lateinit var binding: FragmentAlertBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlertBinding.inflate(layoutInflater)



        return binding.root
    }

}