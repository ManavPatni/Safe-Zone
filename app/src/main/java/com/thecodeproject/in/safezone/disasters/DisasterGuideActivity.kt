package com.thecodeproject.`in`.safezone.disasters

import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.thecodeproject.`in`.safezone.databinding.ActivityDisasterGuideBinding


class DisasterGuideActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDisasterGuideBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Enable full-screen mode
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                )
        binding = ActivityDisasterGuideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val url = intent.getStringExtra("url")

        val webSettings: WebSettings = binding.webView.settings
        webSettings.javaScriptEnabled = true


        // Enable responsive layout
        webSettings.useWideViewPort = true;
        webSettings.loadWithOverviewMode = true;

        // Enable pinch to zoom
        webSettings.builtInZoomControls = true;
        webSettings.displayZoomControls = false;
        // Set the WebView to fit the content to the view height
        webSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING;

        // Ensure links open within the WebView instead of a browser
        binding.webView.webViewClient = WebViewClient()

        // Load the URL
        binding.webView.loadUrl(url!!)

    }
}