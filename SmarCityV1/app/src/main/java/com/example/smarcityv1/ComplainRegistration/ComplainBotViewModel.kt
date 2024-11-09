package com.example.smarcityv1.ComplainRegistration


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val api = RetrofitClient.apiService

    fun requestService(details: String) {
        viewModelScope.launch {
            try {
                val serviceRequest = ServiceRequest(
                    name = "Default Name",
                    address = "Default Address",
                    phoneNumber = "Default Phone",
                    email = "default@example.com",
                    writtenDetails = details
                )
                val response = api.requestService(serviceRequest)
                // Handle response
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun raiseComplaint(category: String, type: String, details: String) {
        viewModelScope.launch {
            try {
                val complaintRequest = ComplaintRequest(
                    name = "Default Name",
                    address = "Default Address",
                    phoneNumber = "Default Phone",
                    email = "default@example.com",
                    category = category,
                    type = type,
                    writtenDetails = details
                )
                val response = api.raiseComplaint(complaintRequest)
                // Handle response
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun sendChatbotQuery(query: String, onResult: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = api.sendChatbotQuery(ChatbotQuery(query))
                if (response.isSuccessful) {
                    onResult(response.body()?.response ?: "No response")
                } else {
                    onResult("Error: ${response.message()}")
                }
            } catch (e: Exception) {
                onResult("Exception: ${e.message}")
            }
        }
    }
}
