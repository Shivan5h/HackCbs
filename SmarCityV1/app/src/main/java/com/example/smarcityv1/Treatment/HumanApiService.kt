package com.example.smarcityv1.Treatment

import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/predict")
    suspend fun predictDisease(@Body symptoms: SymptomInput): PredictionResponse
}