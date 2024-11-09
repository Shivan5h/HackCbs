package com.example.smarcityv1.DocumetSummerizer


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

data class LegalTextRequest(val legal_text: String)
data class SimplifiedTextResponse(val simplified_text: String)

interface ApiService {
    @POST("/simplify")
    suspend fun simplifyLegalText(@Body request: LegalTextRequest): SimplifiedTextResponse
}

object RetrofitInstance {
    private const val BASE_URL = "https://3387ab99-c81c-4281-9e98-c00dda210e5f-00-3g7aaaapw29y5.picard.replit.dev/"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}