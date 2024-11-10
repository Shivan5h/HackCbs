package com.example.smarcityv1.GovPolls

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
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
fun DisplayPollScreen(navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    var polls by remember { mutableStateOf(listOf<Poll>()) }

    val retrofit = Retrofit.Builder()
        .baseUrl("https://3387ab99-c81c-4281-9e98-c00dda210e5f-00-3g7aaaapw29y5.picard.replit.dev/")
        .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().add(KotlinJsonAdapterFactory()).build()))
        .build()

    val apiService = retrofit.create(ApiService::class.java)

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                polls = apiService.getPolls()
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Participate in Poll") }
            )
        },
        content = { paddingValues ->
            LazyColumn(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
                items(polls) { poll ->
                    Card(modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { navController.navigate("ParticipatePollScreen/${poll.id}") }) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = poll.question)
                        }
                    }
                }
            }
        }
    )
}