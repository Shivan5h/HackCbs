package com.example.smarcityv1.mainScreen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun BottomNavigationBar() {
    BottomAppBar(
        containerColor = Color.Black,
        contentColor = Color.White
    ) {
        IconButton(onClick = { /* Handle Navigation */ }) {
            Icon(Icons.Default.Home, contentDescription = "Home")
        }
        Spacer(modifier = Modifier.weight(1f, true)) // Center the items
        IconButton(onClick = { /* Handle Other Action */ }) {
            Icon(Icons.Default.Notifications, contentDescription = "Notifications")
        }
        IconButton(onClick = { /* Handle Another Action */}) {
            Icon(Icons.Default.MoreVert, contentDescription = "More")
        }
    }
}