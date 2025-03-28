package com.example.myapplication

import android.content.Intent
import android.net.Uri
import android.util.Log
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService

class LocationReceiver: WearableListenerService() {
    companion object {
        private const val TAG = "LocationReceiver"
        private const val PATH_NAVIGATION = "/start_navigation"
    }
    override fun onMessageReceived(messageEvent: MessageEvent) {
        if (messageEvent.path == PATH_NAVIGATION) {
            val destination = String(messageEvent.data)
            Log.d(TAG, "Received navigation request for: $destination")
            startGoogleMapsNavigation(destination)
        }
    }

    private fun startGoogleMapsNavigation(destination: String) {
        try {
            val gmmIntentUri = Uri.parse("google.navigation:q=${Uri.encode(destination)}&mode=d")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri).apply {
                `package` = "com.google.android.apps.maps"
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
                startActivity(mapIntent)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to start Google Maps navigation", e)
        }
    }

}