// InflationScreen.kt
package com.example.smarcityv1.GovData

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InflationScreen(navController: NavController, viewModel: InflationViewModel = viewModel()) {
    val inflationData by viewModel.inflationData.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Government Data", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Blue)
            )
        }
    ) { paddingValues ->
        Log.d("InflationScreen", "Data size: ${inflationData.size}")  // Log the size of the data

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            items(inflationData) { data ->
                Log.d("InflationScreen", "Year: ${data.financial_year}, Inflation Rate: ${data.cpi_c_inflation_}")  // Log each item
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Year: ${data.financial_year}", fontSize = 18.sp, color = Color.Black)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Inflation Rate: ${data.cpi_c_inflation_}%", fontSize = 16.sp, color = Color.Gray)
                    }
                }
            }
        }
    }
}