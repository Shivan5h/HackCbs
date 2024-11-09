package com.example.smarcityv1.mainScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException


class SmartCityViewModel : ViewModel() {
    private val _news = MutableLiveData<List<NewsArticle>>()
    val news: LiveData<List<NewsArticle>> get() = _news

    init {
        refreshNews()
    }

    fun refreshNews() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getTopHeadlines()
                Log.d("API Response", response.toString())  // Log the response
                _news.value = response.articles
                Log.d("News List Size", "Size: ${response.articles.size}")  // Log the size of the news list
            } catch (e: IOException) {
                Log.e("Network Error", e.message.toString())  // Log network error
            } catch (e: HttpException) {
                Log.e("HTTP Error", e.message.toString())  // Log HTTP error
            }
        }
    }
}