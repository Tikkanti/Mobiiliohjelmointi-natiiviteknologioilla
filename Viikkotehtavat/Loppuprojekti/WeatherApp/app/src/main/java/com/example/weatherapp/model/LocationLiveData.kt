package com.example.weatherapp.model

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import com.google.android.gms.location.LocationServices


class LocationLiveData(private val context: Context) : LiveData<Coordinates>() {
    val fusedLocationClient =
        LocationServices.getFusedLocationProviderClient(context)

    public fun getLocationData() {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val request = com.google.android.gms.location.LocationRequest.Builder(
            com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY,
            1000 // 1 second interval
        ).build()

        fusedLocationClient.requestLocationUpdates(
            request,
            object : com.google.android.gms.location.LocationCallback() {
                override fun onLocationResult(result: com.google.android.gms.location.LocationResult) {
                    val loc = result.lastLocation
                    android.util.Log.d("LocationLiveData", "Update location: $loc")
                    setLocationData(loc)
                }
            },
            null
        )
    }


    private fun setLocationData(location: Location?) {
        location?.let {
            value = Coordinates(it.latitude,it.longitude)
        }
    }
}