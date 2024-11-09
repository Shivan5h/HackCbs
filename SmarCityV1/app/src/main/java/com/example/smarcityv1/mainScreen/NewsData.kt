package com.example.smarcityv1.mainScreen

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<NewsArticle>
)

data class NewsArticle(
    val title: String,
    val description: String
)