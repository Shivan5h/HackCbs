package com.example.smarcityv1.GovData

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class InflationViewModel : ViewModel() {
    private val _inflationData = MutableStateFlow<List<InflationData>>(emptyList())
    val inflationData: StateFlow<List<InflationData>> = _inflationData

    init {
        fetchInflationData()
    }

    private fun fetchInflationData() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getInflationData(
                    apiKey = "579b464db66ec23bdd000001cdd3946e44ce4aad7209ff7b23ac571b"
                )
                Log.d("API Response", response.toString())  // Log the raw response
                _inflationData.value = response.records
            } catch (e: IOException) {
                Log.e("Network Error", "IOException: ${e.message}", e)  // Log network error
            } catch (e: HttpException) {
                Log.e("HTTP Error", "HttpException: ${e.message}", e)  // Log HTTP error
            } catch (e: Exception) {
                Log.e("Unknown Error", "Exception: ${e.message}", e)  // Log any other errors
            }
        }
    }
}