package com.example.smarcityv1.GovPolls

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePollScreen(viewModel: PollViewModel = viewModel()) {
    var question by remember { mutableStateOf("") }
    var responseMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    // Observe LiveData outside the onClick lambda
    val createPollSuccess by viewModel.createPollSuccess.observeAsState()
    val errorMessage by viewModel.errorMessage.observeAsState()

    // Update state based on LiveData changes
    LaunchedEffect(createPollSuccess, errorMessage) {
        createPollSuccess?.let { successMessage ->
            responseMessage = successMessage
            isLoading = false
        }
        errorMessage?.let { error ->
            responseMessage = error
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Create Poll") })
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(16.dp)
                ) {
                    TextField(
                        value = question,
                        onValueChange = { question = it },
                        label = { Text("Poll Question") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            isLoading = true
                            viewModel.createPoll(question)
                        },
                        enabled = question.isNotEmpty()
                    ) {
                        Text("Create Poll")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    if (isLoading) {
                        CircularProgressIndicator()
                    } else {
                        Text(responseMessage)
                    }
                }
            }
        }
    )
}