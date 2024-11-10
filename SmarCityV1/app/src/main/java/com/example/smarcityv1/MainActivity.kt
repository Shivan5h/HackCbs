package com.example.smarcityv1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.smarcityv1.mainScreen.BottomNavigationBar
import com.example.smarcityv1.mainScreen.MainContent
import com.example.smarcityv1.mainScreen.SmartCityViewModel
import com.example.smarcityv1.mainScreen.TopBar
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.smarcityv1.ComplainRegistration.ChatbotScreen
import com.example.smarcityv1.ComplainRegistration.MainViewModel
import com.example.smarcityv1.DocumetSummerizer.SimplifyScreen
import com.example.smarcityv1.GovPolls.CreatePollScreen
import com.example.smarcityv1.GovPolls.DisplayPollScreen
import com.example.smarcityv1.GovPolls.DisplayResultsScreen
import com.example.smarcityv1.GovPolls.ParticipatePollScreen
import com.example.smarcityv1.GovPolls.PollResultsScreen
import com.example.smarcityv1.GovPolls.PollScreen
import com.example.smarcityv1.LoginScreen.LoginScreen
import com.example.smarcityv1.LoginScreen.LoginViewModel
import com.example.smarcityv1.SingupScreen.NextScreen
import com.example.smarcityv1.SingupScreen.SignUpScreen
import com.example.smarcityv1.SingupScreen.SignUpViewModel
import com.example.smarcityv1.Treatment.AnimalTreatmentScreen
import com.example.smarcityv1.Treatment.HumanTreatmentScreen
import com.example.smarcityv1.Treatment.TreatmentOption
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LaunchedEffect(Unit) {
                requestLocationPermission()
            }
            SmartCityApp()
        }
    }

    private fun requestLocationPermission() {
        val permission = Manifest.permission.ACCESS_FINE_LOCATION
        when {
            ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED -> {
            }
            ActivityCompat.shouldShowRequestPermissionRationale(this, permission) -> {
            }
            else -> {
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
    val ChatbotViewModel: MainViewModel = viewModel()

    Scaffold(
        topBar = { if (showBars) TopBar() },
        bottomBar = { if (showBars) BottomNavigationBar() }
    ) { paddingValues ->
        NavHost(navController = navController, startDestination = "LoginScreen") {
            composable("mainContent") {
                showBars = true
                MainContent(paddingValues, viewModel, navController)
            }

            composable("LoginScreen") {
                showBars = false
                LoginScreen(loginViewModel, navController)
            }
            composable("SignupScreen") {
                showBars = false
                SignUpScreen(navController, singupViewModel)
            }
            composable("NextScreen") {
                showBars = false
                NextScreen(singupViewModel, navController)
            }

            composable("TreatmentScreen"){
                showBars= false
                TreatmentOption(navController)
            }

            composable("HumanTreatment"){
                showBars = false
                HumanTreatmentScreen()
            }

            composable("AnimalTreatment") {
                showBars=false
                AnimalTreatmentScreen()
            }

            composable("simplifierScreen") {
                showBars = false
                SimplifyScreen()
            }
            composable("ComplainChatbot") {
                showBars = false
                ChatbotScreen(ChatbotViewModel)
            }
            composable("PollScreen") { PollScreen(navController) }
            composable("CreatePollScreen") { CreatePollScreen(navController) }
            composable("PollListScreen") { DisplayPollScreen(navController) }
            composable("DisplayResultsScreen") { DisplayResultsScreen(navController) }
            composable("ParticipatePollScreen/{pollId}") { backStackEntry ->
                val pollId = backStackEntry.arguments?.getString("pollId")
                ParticipatePollScreen(navController, pollId)
            }
            composable("PollResultsScreen/{pollId}") { backStackEntry ->
                val pollId = backStackEntry.arguments?.getString("pollId")
                PollResultsScreen(navController, pollId)
            }
        }
    }
}