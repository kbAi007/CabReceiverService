package com.example.myapplication

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onResume() {
//        openGoogleMapsWithSearch("")
        super.onResume()
    }

    private fun openGoogleMapsWithSearch(query: String) {
        val gmmIntentUri = Uri.parse("google.navigation:q="+ Uri.encode("Botanical Garden Metro")) // Search for places with given query
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps") // Force Google Maps app

        // Start Google Maps on the watch
//        mapIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(mapIntent)
    }
}