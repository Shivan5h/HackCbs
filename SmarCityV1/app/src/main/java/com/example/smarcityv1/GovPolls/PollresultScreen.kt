package com.example.smarcityv1.GovPolls

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
fun PollResultsScreen(navController: NavController, pollId: String?) {
    val coroutineScope = rememberCoroutineScope()
    var pollResponse by remember { mutableStateOf<PollResponse?>(null) }

    val retrofit = Retrofit.Builder()
        .baseUrl("https://3387ab99-c81c-4281-9e98-c00dda210e5f-00-3g7aaaapw29y5.picard.replit.dev/")
        .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().add(KotlinJsonAdapterFactory()).build()))
        .build()

    val apiService = retrofit.create(ApiService::class.java)

    LaunchedEffect(pollId) {
        coroutineScope.launch {
            try {
                pollId?.let {
                    pollResponse = apiService.getPollResults(it.toInt())
                }
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Poll Results") }
            )
        },
        content = { paddingValues ->
            pollResponse?.let { response ->
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(16.dp)
                ) {
                    Text(text = response.question, style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.height(16.dp))
                    LazyColumn {
                        items(response.responses) { item ->
                            Text(text = item, modifier = Modifier.padding(vertical = 4.dp))
                        }
                    }
                }
            }
        }
    )
}