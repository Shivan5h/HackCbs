package com.example.smarcityv1.mapScreen

data class DirectionsResponse(
    val routes: List<Route>
)

data class Route(
    val legs: List<Leg>
)

data class Leg(
    val steps: List<Step>
)

data class Step(
    val start_location: LatLng,
    val end_location: LatLng
)

data class LatLng(
    val lat: Double,
    val lng: Double
)