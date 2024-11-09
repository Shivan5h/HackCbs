package com.example.smarcityv1.LoginScreen

// LoginActivity.kt
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController

class LoginActivity : ComponentActivity() {
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        setContent {
            LoginScreen(viewModel = viewModel , rememberNavController())
        }
    }
}
