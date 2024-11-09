package com.example.smarcityv1.mapScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarcityv1.mapScreen.RetrofitInstance
import com.google.maps.DirectionsApi
import com.google.maps.GeoApiContext
import com.google.maps.internal.PolylineEncoding
import com.google.maps.model.LatLng
import com.google.maps.model.TravelMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    var selectedTab by mutableStateOf(0)
    var routeCoordinates by mutableStateOf(listOf<LatLng>())

    fun onTabSelected(index: Int) {
        selectedTab = index
    }

    fun fetchRoute(origin: LatLng, destination: LatLng, apiKey: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitInstance.api.getDirections(
                    origin = "${origin.lat},${origin.lng}",
                    destination = "${destination.lat},${destination.lng}",
                    apiKey = apiKey
                )
                if (response.routes.isNotEmpty()) {
                    val steps = response.routes[0].legs[0].steps
                    routeCoordinates = steps.flatMap { listOf(it.start_location, it.end_location) }
                        .map { LatLng(it.lat, it.lng) }
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}