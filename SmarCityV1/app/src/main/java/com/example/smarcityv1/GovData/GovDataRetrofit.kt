package com.example.smarcityv1.GovData

// ApiService.kt


import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("resource/352b3616-9d3d-42e5-80af-7d21a2a53fab")
    suspend fun getInflationData(
        @Query("api-key") apiKey: String,
        @Query("format") format: String = "json"
    ): ApiResponse
}