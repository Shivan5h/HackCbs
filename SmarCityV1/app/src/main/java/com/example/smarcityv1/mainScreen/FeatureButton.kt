package com.example.smarcityv1.mainScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.*

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.NavController


@Composable
fun FeatureCards(navController: NavController) {
    // Get screen height to calculate 40% of it
    val screenHeight = LocalConfiguration.current.screenHeightDp
    val top40Height = (screenHeight * 0.4).dp  // 40% of screen height

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.4f)  // Cover the top 40% of the screen
            .padding(0.dp),       // No padding at edges
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(top40Height / 2),  // Divide top 40% into two equal rows
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FeatureCard("AR Navigator", modifier = Modifier.weight(1f)){
                navController.navigate("mapScreen") // Navigate to MapScreen
            }
            FeatureCard("Civic Issue Raiser", modifier = Modifier.weight(1f)){
                navController.navigate("CivicIssueChatBot") // Navigate to CivicIssueScreen
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(top40Height / 2),  // Second row for two cards
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FeatureCard("Mental Health Chatbot", modifier = Modifier.weight(1f)) {
                navController.navigate("chatBotScreen") // Navigate to ChatBotScreen
            }
            FeatureCard("Govt Schemes", modifier = Modifier.weight(1f)){
                navController.navigate("inflationRage") // Navigate to GovtSchemesScreen
            }
        }
    }
}

@Composable
fun FeatureCard(title: String, modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    Card(
        modifier = modifier
            .padding(8.dp)  // Minimal internal padding
            .fillMaxHeight() // Ensure the card fills the row height
            .fillMaxWidth()
            .clickable { onClick() },  // Enable click on the card
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp), // Use CardDefaults.cardElevation()
        colors = CardDefaults.cardColors( // Use CardDefaults.cardColors() for colors
            containerColor = Color.White,
            contentColor = Color.Black
        )) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
    }
}