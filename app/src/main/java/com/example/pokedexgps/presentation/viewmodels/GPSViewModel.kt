package com.example.pokedexgps.presentation.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GPSViewModel : ViewModel() {
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var lastLocation: Location? = null
    private val _randomPokemonNumber = MutableStateFlow<Int?>(null)
    val randomPokemonNumber: StateFlow<Int?> = _randomPokemonNumber

    @SuppressLint("MissingPermission")
    fun startLocationUpdates(context: Context) {
        if (fusedLocationClient == null) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        }

        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, 500
        ).build()

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let { checkDistance(it) }
            }
        }
        fusedLocationClient?.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    private fun checkDistance(location: Location) {
        if (lastLocation != null) {
            val distance = location.distanceTo(lastLocation!!)
            if (distance >= 10) {
                generateRandomPokemonNumber()
                lastLocation = location
            }
        } else
            lastLocation = location
    }

    private fun generateRandomPokemonNumber() {
        viewModelScope.launch {
            _randomPokemonNumber.value = (1..151).random()

        }
    }
}