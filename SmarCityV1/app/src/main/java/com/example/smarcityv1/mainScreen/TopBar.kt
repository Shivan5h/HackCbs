package com.example.smarcityv1.mainScreen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(scrollBehavior: TopAppBarScrollBehavior? = null) {
    CenterAlignedTopAppBar(
        title = { Text("Smart City") },
        navigationIcon = {
            IconButton(onClick = { /* Handle Menu Click */ }) {
                Icon(Icons.Default.Menu, contentDescription = "Menu")
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Cyan,
            titleContentColor = Color.White,
            navigationIconContentColor =Color.White,
            actionIconContentColor = Color.White
        ),
        scrollBehavior = scrollBehavior
    )
}
