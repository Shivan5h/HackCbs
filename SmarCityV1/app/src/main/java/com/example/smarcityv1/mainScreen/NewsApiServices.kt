package com.example.smarcityv1.mainScreen

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET



interface NewsApiService {
    @GET("v2/top-headlines?country=us&apiKey=aa78c6d22aa142619891b7d9b8954289")
    suspend fun getTopHeadlines(): NewsResponse
}

object RetrofitInstance {
    val api: NewsApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://newsapi.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApiService::class.java)
    }
}