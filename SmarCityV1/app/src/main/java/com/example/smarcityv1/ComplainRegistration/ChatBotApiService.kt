package com.example.smarcityv1.ComplainRegistration
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("request-service")
    suspend fun requestService(@Body serviceRequest: ServiceRequest): Response<Unit>

    @POST("raise-complaint")
    suspend fun raiseComplaint(@Body complaintRequest: ComplaintRequest): Response<Unit>

    @POST("chatbot")
    suspend fun sendChatbotQuery(@Body query: ChatbotQuery): Response<ChatbotResponse>
}

data class ChatbotQuery(val input: String)
data class ChatbotResponse(val response: String)