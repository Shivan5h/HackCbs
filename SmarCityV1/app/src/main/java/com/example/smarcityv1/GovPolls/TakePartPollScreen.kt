package com.example.smarcityv1.GovPolls

import androidx.compose.foundation.layout.*
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
fun ParticipatePollScreen(navController: NavController, pollId: String?) {
    var response by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://3387ab99-c81c-4281-9e98-c00dda210e5f-00-3g7aaaapw29y5.picard.replit.dev/")
        .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().add(KotlinJsonAdapterFactory()).build()))
        .build()

    val apiService = retrofit.create(ApiService::class.java)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Participate in Poll") }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                TextField(value = response, onValueChange = { response = it }, label = { Text("Your Response") })
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    coroutineScope.launch {
                        try {
                            pollId?.let {
                                apiService.participate(ParticipateRequest(it.toInt(), response))
                                navController.navigate("PollListScreen")
                            }
                        } catch (e: Exception) {
                            // Handle error
                        }
                    }
                }) {
                    Text("Submit Response")
                }
            }
        }
    )
}