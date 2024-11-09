package com.example.smarcityv1.LoginScreen

// LoginScreen.kt
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.smarcityv1.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(viewModel: LoginViewModel , navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val loginStatus by viewModel.loginStatus.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1D74FF))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.welcome_image),
            contentDescription = "Welcome Image",
            modifier = Modifier.size(200.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Welcome Citizen\nLogin",
            color = Color.White,
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email/Username") },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White), // Background color
            textStyle = TextStyle(color = Color.Black), // Text color
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = Color.Black, // Cursor color
                focusedIndicatorColor = Color.Blue, // Indicator color when focused
                unfocusedIndicatorColor = Color.Gray // Indicator color when not focused
            )
        )


    Spacer(Modifier.height(16.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White), // Background color
            textStyle = TextStyle(color = Color.Black), // Text color
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = Color.Black,
                focusedIndicatorColor = Color.Blue,
                unfocusedIndicatorColor = Color.Gray
            ),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Forgot Password? Click me",
            color = Color.White,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Button(
            onClick = { viewModel.login(email, password)
                      navController.navigate("mainContent")},
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(text = "Sign up")
        }

        Spacer(modifier = Modifier.height(8.dp))

        ClickableText(
            text = AnnotatedString("Don't have an account? Click me"),
            onClick = { navController.navigate("SignupScreen") },
            style = LocalTextStyle.current.copy(color = Color.White)
        )

        // Handle login status
        when (loginStatus) {
            is LoginStatus.Success -> Text("Login Successful", color = Color.Green)
            is LoginStatus.Failure -> Text("Login Failed: ${(loginStatus as LoginStatus.Failure).message}", color = Color.Red)
            else -> {}
        }
    }
}
