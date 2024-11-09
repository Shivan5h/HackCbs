package com.example.smarcityv1.DocumetSummerizer

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SimplifyScreen(viewModel: SimplifyViewModel = viewModel()) {
    var legalText by remember { mutableStateOf("") }
    val simplifiedText by viewModel.simplifiedText.observeAsState("")
    val error by viewModel.error.observeAsState("")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = legalText,
            onValueChange = { legalText = it },
            label = { Text("Enter Legal Text") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { viewModel.simplifyText(legalText) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Simplify")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Simplified Text: $simplifiedText")
        if (error.isNotEmpty()) {
            Text("Error: $error", color = MaterialTheme.colorScheme.error)
        }
    }
}