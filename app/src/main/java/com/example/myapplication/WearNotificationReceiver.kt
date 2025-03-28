package com.example.myapplication

import android.util.Log
import com.google.android.gms.wearable.*
import androidx.core.app.NotificationCompat
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class WearNotificationReceiver : WearableListenerService() {
    companion object {
        private const val TAG = "WearNotificationReceiver"
        private const val PATH_UBER_NOTIFICATION = "/ola_notification"
        private const val PATH_AMAZON_NOTIFICATION = "/amazon_notification"
        private const val PATH_BMS_NOTIFICATION = "/bms_notification"
        private const val PATH_FLIGHT_NOTIFICATION = "/flight_notification"
        private const val SEARCH_QUERY =
            "Botanical Garden Metro" // Search term (Dynamic input possible)
        const val ACTION_PIN_RECEIVED = "ACTION_PIN_RECEIVED"
    }

    override fun onMessageReceived(messageEvent: MessageEvent) {
        val notificationText = String(messageEvent.data)
        var intent: Intent? =null
        if (messageEvent.path == PATH_UBER_NOTIFICATION) {

            }
        else if (messageEvent.path == PATH_AMAZON_NOTIFICATION) {
                val otpRegex = Regex("OTP[-:\\s]+(\\d{4,6})", RegexOption.IGNORE_CASE)
                val matchResult = otpRegex.find(notificationText)
                if (matchResult != null) {
                    val extractedOTP = matchResult.groupValues[1]
                    Log.d(TAG, "Extracted OTP: $extractedOTP")

                    // Start PinDisplayActivity with OTP
                    intent = Intent(this, MainActivity::class.java).apply {
                        flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK  // Required for launching activity from service
                        putExtra("pin_value", extractedOTP)
                        putExtra("key","1")
                    }
                }
            }
        else if(messageEvent.path== PATH_BMS_NOTIFICATION)
            {
                val moviePattern = Regex("Your movie (.+?) is")  // Extracts movie name
                val timePattern = Regex("at (\\d{1,2}[APMapm]+)")  // Extracts time (e.g., 7PM, 10AM)
                val seatPattern = Regex("seat number is ([A-Za-z0-9]+)")  // Extracts seat number (e.g., B22)

                val movieName = moviePattern.find(notificationText)?.groups?.get(1)?.value ?: "Not Found"
                val movieTime = timePattern.find(notificationText)?.groups?.get(1)?.value ?: "Not Found"
                val seatNumber = seatPattern.find(notificationText)?.groups?.get(1)?.value ?: "Not Found"
                Log.d(TAG, "movieName : $movieName")
                intent = Intent(this, MainActivity::class.java).apply {
                    flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK  // Required for launching activity from service
                    putExtra("movieName", movieName)
                    putExtra("movieTime", movieTime)
                    putExtra("seatNumber", seatNumber)
                    putExtra("key","2")

                }
            }
        else if(messageEvent.path==PATH_FLIGHT_NOTIFICATION)
            {
                val flightPattern = Regex("Flight\\s([A-Za-z0-9-]+)")
                val gatePattern = Regex("Gate\\s(\\d+)")
                val seatPattern = Regex("seat number is\\s([A-Za-z0-9]+)")

                val flightNumber = flightPattern.find(notificationText)?.groups?.get(1)?.value ?: "Not Found"
                val gateNumber = gatePattern.find(notificationText)?.groups?.get(1)?.value ?: "Not Found"
                val seatNumber = seatPattern.find(notificationText)?.groups?.get(1)?.value ?: "Not Found"
                Log.d(TAG, "flightNumber : $flightNumber")

                intent = Intent(this, MainActivity::class.java).apply {
                    flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK  // Required for launching activity from service
                    putExtra("flightNumber", flightNumber)
                    putExtra("gateNumber", gateNumber)
                    putExtra("seatNumber", seatNumber)
                    putExtra("key","3")
                }
            }

        startActivity(intent)

    }

        private fun showNotification(message: String) {
            val notificationId = 1001
            val notification = NotificationCompat.Builder(this, "uber_channel")
                .setContentTitle("Uber Alert")
                .setContentText(message)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(longArrayOf(100, 200, 300))
                .setAutoCancel(true)
                .build()

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(notificationId, notification)
        }

        private fun openGoogleMapsWithSearch(query: String) {
            val gmmIntentUri =
                Uri.parse("google.navigation:q=" + Uri.encode("Botanical Garden Metro")) // Search for places with given query
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps") // Force Google Maps app

            // Start Google Maps on the watch
            mapIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(mapIntent)
        }
    fun extractFlightDetails(text: String) {
        val flightPattern = Regex("Flight\\s([A-Za-z0-9-]+)")
        val gatePattern = Regex("Gate\\s(\\d+)")
        val seatPattern = Regex("seat number is\\s([A-Za-z0-9]+)")

        val flightNumber = flightPattern.find(text)?.groups?.get(1)?.value ?: "Not Found"
        val gateNumber = gatePattern.find(text)?.groups?.get(1)?.value ?: "Not Found"
        val seatNumber = seatPattern.find(text)?.groups?.get(1)?.value ?: "Not Found"

        println("Flight Number: $flightNumber")
        println("Gate Number: $gateNumber")
        println("Seat Number: $seatNumber")
    }
    }

