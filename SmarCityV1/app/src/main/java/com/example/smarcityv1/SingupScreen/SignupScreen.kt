package com.example.smarcityv1.SingupScreen

// SignUpScreen.kt
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.smarcityv1.R

@Composable
fun SignUpScreen(navController: NavController, viewModel: SignUpViewModel) {
    val firstName by viewModel.firstName
    val lastName by viewModel.lastName
    val dateOfBirth by viewModel.dateOfBirth
    val gender by viewModel.gender

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1D74FF))

    ) {
        // Display the top image
        Image(
            painter = painterResource(id = R.drawable.singupscreen), // Add your drawable resource here
            contentDescription = "Sign Up Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Sign In", color = Color.Black)

        Spacer(modifier = Modifier.height(8.dp))

        // First Name Field
        TextField(
            value = firstName,
            onValueChange = { viewModel.onFirstNameChange(it) },
            label = { Text("First Name") },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Last Name Field
        TextField(
            value = lastName,
            onValueChange = { viewModel.onLastNameChange(it) },
            label = { Text("Last Name") },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Date of Birth Field
        TextField(
            value = dateOfBirth,
            onValueChange = { viewModel.onDateOfBirthChange(it) },
            label = { Text("Date Of Birth") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Gender Field
        TextField(
            value = gender,
            onValueChange = { viewModel.onGenderChange(it) },
            label = { Text("Gender") },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Button to navigate to the next screen
        Button(
            onClick = {
                // Add navigation to the next screen here
                navController.navigate("nextScreen")
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Next →")
        }
    }
}