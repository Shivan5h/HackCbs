package com.example.smarcityv1.ComplainRegistration

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarcityv1.Treatment.SymptomInput
import kotlinx.coroutines.launch
import com.example.smarcityv1.Treatment.DiseasePredictionApiService // Adjust package name if needed
import com.example.smarcityv1.Treatment.RetrofitClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST




class HumanTreatmentViewModel : ViewModel() {
    var predictedDisease by mutableStateOf("")
    var treatment by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var error by mutableStateOf("")

    fun predictDisease(symptoms: SymptomInput) {
        viewModelScope.launch {
            isLoading = true
            error = ""
            try {
                val response = RetrofitClient.apiService.predictDisease(symptoms)
                predictedDisease = response.predicted_disease
                treatment = response.treatment
            } catch (e: Exception) {
                error = "Error: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }
}