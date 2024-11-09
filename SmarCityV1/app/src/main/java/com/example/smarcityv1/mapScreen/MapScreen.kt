package com.example.smarcityv1.mapScreen

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@Composable
fun MapScreen(viewModel: MainViewModel = viewModel()) {
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    var currentLocation by remember { mutableStateOf<Location?>(null) }
    val mapProperties by remember {
        mutableStateOf(
            MapProperties(
                isMyLocationEnabled = true
            )
        )
    }
    val mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                myLocationButtonEnabled = true
            )
        )
    }
    LaunchedEffect(Unit) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                currentLocation = location
                location?.let {
                    val origin = com.google.maps.model.LatLng(it.latitude, it.longitude)
                    val destination = com.google.maps.model.LatLng(28.644800, 77.216721) // Delhi Gate coordinates
                    viewModel.fetchRoute(origin, destination, "Your Api Key")
                }
            }
        }
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            currentLocation?.let { LatLng(it.latitude, it.longitude) } ?: LatLng(28.6139, 77.2090), 15f
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = mapProperties,
            uiSettings = mapUiSettings
        ) {
            currentLocation?.let {
                val currentLatLng = LatLng(it.latitude, it.longitude)
                val currentLocationState = rememberMarkerState(position = currentLatLng)
                Marker(
                    state = currentLocationState,
                    title = "Current Location"
                )
                val destinationLatLng = LatLng(28.644800, 77.216721) // Delhi Gate coordinates
                val destinationState = rememberMarkerState(position = destinationLatLng)
                Marker(
                    state = destinationState,
                    title = "Delhi Gate"
                )
                Polyline(
                    points = viewModel.routeCoordinates.map { LatLng(it.lat, it.lng) },
                    color = Color.Blue,
                    width = 5f
                )
            }
        }

        Button(
            onClick = {
                val intent = Intent(context, ARActivity::class.java).apply {
                    putExtra("model", "burger")
                    putExtra("latitude", currentLocation?.latitude ?: 0.0)
                    putExtra("longitude", currentLocation?.longitude ?: 0.0)
                    putParcelableArrayListExtra("routeCoordinates", ArrayList(viewModel.routeCoordinates.map {
                        Location("").apply {
                            latitude = it.lat
                            longitude = it.lng
                        }
                    }))
                }
                context.startActivity(intent)
            },
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        ) {
            Text("AR View")
        }
    }
}