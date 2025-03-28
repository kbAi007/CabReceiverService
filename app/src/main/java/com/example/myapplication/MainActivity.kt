package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.transition.Visibility

class MainActivity : AppCompatActivity() {

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
    /*    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
        var bg=findViewById<ConstraintLayout>(R.id.mainView)
        var icon=findViewById<ImageView>(R.id.icon)
        var text1=findViewById<TextView>(R.id.text1)
        var text2=findViewById<TextView>(R.id.text2)
        var text3=findViewById<TextView>(R.id.text3)
        if(intent.getStringExtra("key")=="2")
        {
            val seatNumber=intent.getStringExtra("seatNumber")?:"N/A"
            val movieName=intent.getStringExtra("movieName")?:"N/A"
            val movieTime=intent.getStringExtra("movieTime")?:"N/A"
            text1.text="Seat ${seatNumber}"
            text2.text=movieName
            text3.text="${movieTime} | IMAX"
            icon.setBackgroundResource(R.drawable.bms)
            bg.setBackgroundResource(R.drawable.mmt_background)
        }
        else if(intent.getStringExtra("key")=="3")
        {
            val flightNumber =intent.getStringExtra("flightNumber")?:"N/A"
            val gateNumber =intent.getStringExtra("gateNumber")?:"N/A"
            val seatNumber =intent.getStringExtra("seatNumber")?:"N/A"
            text1.text=flightNumber
            text2.text="Gate ${gateNumber} | Seat ${seatNumber}"
            text3.text="Boarding Started"
            icon.setBackgroundResource(R.drawable.indigo)
            bg.setBackgroundResource(R.drawable.flight_background)
        }
        else if(intent.getStringExtra("key")=="1")
        {
            val receivedPin = intent.getStringExtra("pin_value") ?: "N/A"
            text1.text=receivedPin
            text2.text="Amazon OTP"
            text3.visibility= View.INVISIBLE
            icon.setBackgroundResource(R.drawable.amazon_logo)
            bg.setBackgroundResource(R.drawable.amazon_background)
        }

    }
}