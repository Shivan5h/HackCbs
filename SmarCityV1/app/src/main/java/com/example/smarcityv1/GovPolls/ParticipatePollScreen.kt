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
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParticipatePollScreen(navController: NavController, pollId: Int, viewModel: PollViewModel = viewModel()) {
    var response by remember { mutableStateOf("") }
    var responseMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    // Observe LiveData outside the onClick lambda
    val participatePollSuccess by viewModel.participatePollSuccess.observeAsState()
    val errorMessage by viewModel.errorMessage.observeAsState()

    // Update state based on LiveData changes
    LaunchedEffect(participatePollSuccess, errorMessage) {
        participatePollSuccess?.let { successMessage ->
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
            TopAppBar(title = { Text("Participate in Poll") })
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
                        value = response,
                        onValueChange = { response = it },
                        label = { Text("Your Response") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            isLoading = true
                            viewModel.participateInPoll(pollId, response)
                        },
                        enabled = response.isNotEmpty()
                    ) {
                        Text("Submit Response")
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