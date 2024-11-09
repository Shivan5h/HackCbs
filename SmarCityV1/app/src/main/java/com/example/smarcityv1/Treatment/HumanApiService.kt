package com.example.smarcityv1.Treatment

import retrofit2.http.Body
import retrofit2.http.POST


// Data classes for API request and response
data class SymptomInput(
    val fever: Int,
    val cough: Int,
    val fatigue: Int,
    val difficulty_breathing: Int,
    val age: Int,
    val gender: Int,
    val blood_pressure: Int,
    val cholesterol_level: Int
)

data class PredictionResponse(
    val predicted_disease: String,
    val treatment: String
)
interface DiseasePredictionApiService {
    @POST("/predict")
    suspend fun predictDisease(@Body symptoms: SymptomInput): PredictionResponse
}