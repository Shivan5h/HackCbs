package com.example.smarcityv1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.smarcityv1.mainScreen.BottomNavigationBar
import com.example.smarcityv1.mainScreen.MainContent
import com.example.smarcityv1.mainScreen.SmartCityViewModel
import com.example.smarcityv1.mainScreen.TopBar
import com.example.smarcityv1.ui.theme.SmarCityV1Theme
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.smarcityv1.CivicIssuesChatBot.CivicChatBot
import com.example.smarcityv1.MentalHealthChatBot.ChatBot
import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.smarcityv1.GovData.InflationScreen
import com.example.smarcityv1.GovData.InflationViewModel
import com.example.smarcityv1.LoginScreen.LoginScreen
import com.example.smarcityv1.LoginScreen.LoginViewModel
import com.example.smarcityv1.SingupScreen.NextScreen
import com.example.smarcityv1.SingupScreen.SignUpScreen
import com.example.smarcityv1.SingupScreen.SignUpViewModel
import com.example.smarcityv1.mapScreen.MapScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestLocationPermission()
        setContent {
            SmartCityApp()
        }
    }

    private fun requestLocationPermission() {
        val permission = Manifest.permission.ACCESS_FINE_LOCATION
        when {
            ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED -> {
                // Permission is granted, proceed with displaying the map
            }
            ActivityCompat.shouldShowRequestPermissionRationale(this, permission) -> {
                // Show an explanation to the user why the permission is needed
            }
            else -> {
                // Request the permission
                requestPermissionLauncher.launch(permission)
            }
        }
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission is granted, proceed with displaying the map
        } else {
            // Permission is denied, handle accordingly
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmartCityApp(viewModel: SmartCityViewModel = viewModel()) {
    val navController = rememberNavController()
    var showBars by remember { mutableStateOf(true) }
    val loginViewModel: LoginViewModel = viewModel()
    val singupViewModel: SignUpViewModel = viewModel()
    val inflationViewModel : InflationViewModel = viewModel()


    Scaffold(
        topBar = { if (showBars) TopBar() },
        bottomBar = { if (showBars) BottomNavigationBar() }
    ) { paddingValues ->
        NavHost(navController = navController, startDestination = "LoginScreen") {
            composable("mainContent") {
                showBars = true
                MainContent(paddingValues, viewModel, navController)
            }
            composable("chatBotScreen") {
                showBars = false
                ChatBot()
            }

            composable("CivicIssueChatBot"){
                showBars = false
                CivicChatBot()
            }

            composable("mapScreen") {
                showBars = false
                MapScreen()
            }

            composable("LoginScreen"){
                showBars = false
                LoginScreen(loginViewModel , navController)
            }

            composable("SignupScreen") {
                showBars  = false
                SignUpScreen(navController,singupViewModel)
            }

            composable("NextScreen") {
                showBars = false
                NextScreen(singupViewModel , navController)
            }

            composable("inflationRage") {
                showBars = false
                InflationScreen(navController,inflationViewModel)
            }

        }
    }
}