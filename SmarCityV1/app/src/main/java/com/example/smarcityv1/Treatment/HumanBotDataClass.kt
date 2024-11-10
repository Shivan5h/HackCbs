package com.example.smarcityv1.Treatment

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
