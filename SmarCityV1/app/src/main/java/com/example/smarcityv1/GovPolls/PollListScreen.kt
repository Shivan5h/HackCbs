package com.example.smarcityv1.GovPolls

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.IOException
import org.json.JSONArray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PollListScreen(navController: NavController) {
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf("") }
    var polls by remember { mutableStateOf(listOf<Poll>()) }

    LaunchedEffect(Unit) {
        launch {
            fetchPolls(
                onSuccess = { fetchedPolls ->
                    polls = fetchedPolls
                    isLoading = false

                    // Print poll data to Logcat
                    for (poll in polls) {
                        Log.d("PollListScreen", "Poll ID: ${poll.id}, Question: ${poll.question}")
                    }
                },
                onError = { error ->
                    errorMessage = error
                    isLoading = false
                }
            )
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Polls") }
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (isLoading) {
                    CircularProgressIndicator()
                } else if (errorMessage.isNotEmpty()) {
                    Text(errorMessage)
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        polls.forEach { poll ->
                            Text(poll.question, modifier = Modifier.padding(8.dp))
                        }
                    }
                }
            }
        }
    )
}

data class Poll(val id: Int, val question: String)

suspend fun fetchPolls(onSuccess: (List<Poll>) -> Unit, onError: (String) -> Unit) {
    val client = OkHttpClient()
    try {
        val request = Request.Builder()
            .url("https://3387ab99-c81c-4281-9e98-c00dda210e5f-00-3g7aaaapw29y5.picard.replit.dev/get_polls")
            .get()
            .build()

        val response = withContext(Dispatchers.IO) {
            client.newCall(request).execute()
        }

        if (!response.isSuccessful) {
            // Handle HTTP errors more specifically
            val errorMessage = when (response.code) {
                400 -> "Bad Request"
                401 -> "Unauthorized"
                404 -> "Not Found"
                500 -> "Internal Server Error"
                else -> "Unexpected error: ${response.code}"
            }
            onError(errorMessage)
            return // Exit the function early
        }

        val responseBody = response.body?.string()
        if (responseBody == null) {
            onError("Empty response body")
            return // Exit the function early
        }

        // Parse JSON and call onSuccess
        val jsonArray = JSONArray(responseBody)
        val polls = mutableListOf<Poll>()
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val poll = Poll(
                id = jsonObject.getInt("id"),
                question = jsonObject.getString("question")
            )
            polls.add(poll)
        }
        onSuccess(polls)

    } catch (e: IOException) {
        // Handle network errors
        Log.e("PollListScreen", "Network error: ${e.message}", e)
        onError("Network error: ${e.message}")
    } catch (e: Exception) {
        // Handle other unexpected errors
        Log.e("PollListScreen", "Error fetching polls: ${e.message}", e)
        onError("Error fetching polls: ${e.message}")
    }
}