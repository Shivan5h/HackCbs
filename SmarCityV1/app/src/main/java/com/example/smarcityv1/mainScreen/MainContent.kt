package com.example.smarcityv1.mainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun MainContent(paddingValues: PaddingValues, viewModel: SmartCityViewModel , navController: NavController) {

    Box(modifier = Modifier.fillMaxSize()) { // Use Box for overlay
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.Cyan),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            FeatureCards(navController = navController) // Pass navController
            NewsSection(viewModel)
        }


    }
}