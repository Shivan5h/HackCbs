package com.example.smarcityv1.SingupScreen

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.smarcityv1.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun NextScreen(viewModel: SignUpViewModel , navcontroller: NavController) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    // State variables for input fields
    var phoneNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var aadharCard by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1D74FF))

    ) {
        Image(
            painter = painterResource(id = R.drawable.singupscreen), // Add your drawable resource here
            contentDescription = "Sign Up Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )

        Text("Verification Screen", color = Color.Black)

        Spacer(modifier = Modifier.height(16.dp))

        // Phone Number Field
        TextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Phone Number") },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Email Field
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Aadhar Card Field
        TextField(
            value = aadharCard,
            onValueChange = { aadharCard = it },
            label = { Text("Enter Password") },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Button to store user data in Firebase
        Button(onClick = {
            // Store user data in Firestore
            val userData = hashMapOf(
                "firstName" to viewModel.firstName.value,
                "lastName" to viewModel.lastName.value,
                "dateOfBirth" to viewModel.dateOfBirth.value,
                "gender" to viewModel.gender.value,
                "phoneNumber" to phoneNumber,
                "email" to email,
                "aadharCard" to aadharCard
            )
            db.collection("users")
                .document(auth.currentUser?.uid ?: "UnknownUser")
                .set(userData)
                .addOnSuccessListener {
                    Toast.makeText(context, "User data stored successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context, "Failed to store data: ${e.message}", Toast.LENGTH_SHORT).show()
                }

            navcontroller.navigate("mainContent")
        }
        ) {
            Text("Submit")
        }
    }
}