package com.example.myapplication

import android.util.Log
import com.google.android.gms.wearable.*
import androidx.core.app.NotificationCompat
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.Uri

class WearNotificationReceiver : WearableListenerService() {
    companion object {
        private const val TAG = "WearNotificationReceiver"
        private const val PATH_UBER_NOTIFICATION = "/ola_notification"
        private const val SEARCH_QUERY = "Botanical Garden Metro" // Search term (Dynamic input possible)
    }

    override fun onMessageReceived(messageEvent: MessageEvent) {
        if (messageEvent.path == PATH_UBER_NOTIFICATION) {
            val notificationText = String(messageEvent.data)
            Log.d(TAG, "Received Uber Notification on Watch: $notificationText")
            showNotification(notificationText)
            openGoogleMapsWithSearch(SEARCH_QUERY)
        }
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

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, notification)
    }

    private fun openGoogleMapsWithSearch(query: String) {
        val gmmIntentUri = Uri.parse("google.navigation:q="+Uri.encode("Botanical Garden Metro")) // Search for places with given query
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps") // Force Google Maps app

        // Start Google Maps on the watch
        mapIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(mapIntent)
    }
}
