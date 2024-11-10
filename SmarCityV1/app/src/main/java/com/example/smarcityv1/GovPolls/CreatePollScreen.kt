package com.example.smarcityv1.GovPolls

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePollScreen(navController: NavController) {
    var question by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://3387ab99-c81c-4281-9e98-c00dda210e5f-00-3g7aaaapw29y5.picard.replit.dev/")
        .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().add(KotlinJsonAdapterFactory()).build()))
        .build()

    val apiService = retrofit.create(ApiService::class.java)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create Poll") }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                TextField(value = question, onValueChange = { question = it }, label = { Text("Poll Question") })
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    coroutineScope.launch {
                        try {
                            val response = apiService.createPoll(CreatePollRequest(question))
                            if (response.isSuccessful) {
                                navController.navigate("PollListScreen")
                            } else {
                                // Handle error
                            }
                        } catch (e: Exception) {
                            // Handle error
                        }
                    }
                }) {
                    Text("Create Poll")
                }
            }
        }
    )
}