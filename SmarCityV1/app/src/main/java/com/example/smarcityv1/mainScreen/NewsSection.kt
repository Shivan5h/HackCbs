package com.example.smarcityv1.mainScreen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun NewsSection(viewModel: SmartCityViewModel) {
    val newsList by viewModel.news.observeAsState(emptyList())
    Log.d("NewsSection", "News list size: ${newsList.size}")  // Log the size of the news list

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Row for Title and Refresh button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "News",
                fontSize = 20.sp,
                color = Color.Black,
                textAlign = TextAlign.Start
            )
            Button(onClick = { viewModel.refreshNews() }) {
                Text("Refresh")
            }
        }

        // LazyColumn to make the news scrollable
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(newsList) { article ->
                NewsCard(article)
            }
        }
    }
}

@Composable
fun NewsCard(newsItem: NewsArticle) {
    val title = newsItem.title ?: "No Title"
    val description = newsItem.description ?: "No Description"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),  // Increased height for better readability
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Column {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = description,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}