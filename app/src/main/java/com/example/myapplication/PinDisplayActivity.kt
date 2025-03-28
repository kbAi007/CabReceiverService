package com.example.myapplication

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class PinDisplayActivity : AppCompatActivity() {
    private lateinit var pinTextView: TextView


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin_display)

        pinTextView = findViewById(R.id.pinTextView)
        val receivedPin = intent.getStringExtra("pin_value") ?: "N/A"

        pinTextView.text = "OTP: $receivedPin"
    }

}
