package com.example.smarcityv1.Treatment

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarcityv1.ComplainRegistration.HumanTreatmentViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HumanTreatmentScreen(viewModel: HumanTreatmentViewModel = hiltViewModel()) {
    var fever by remember { mutableStateOf(0) }
    var cough by remember { mutableStateOf(0) }
    var fatigue by remember { mutableStateOf(0) }
    var difficultyBreathing by remember { mutableStateOf(0) }
    var age by remember { mutableStateOf(0) }
    var gender by remember { mutableStateOf(0) }
    var bloodPressure by remember { mutableStateOf(0) }
    var cholesterolLevel by remember { mutableStateOf(0) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Input fields for symptoms
        OutlinedTextField(
            value = fever.toString(),
            onValueChange = { fever = it.toIntOrNull() ?: 0 },
            label = { Text("Fever (0/1)") }
        )
        OutlinedTextField(
            value = cough.toString(),
            onValueChange = { cough = it.toIntOrNull() ?: 0 },
            label = { Text("Cough (0/1)") }
        )
        OutlinedTextField(
            value = fatigue.toString(),
            onValueChange = { fatigue = it.toIntOrNull() ?: 0 },
            label = { Text("Fatigue (0/1)") }
        )
        OutlinedTextField(
            value = difficultyBreathing.toString(),
            onValueChange = { difficultyBreathing = it.toIntOrNull() ?: 0 },
            label = { Text("Difficulty Breathing (0/1)") }
        )
        OutlinedTextField(
            value = age.toString(),
            onValueChange = { age = it.toIntOrNull() ?: 0 },
            label = { Text("Age") }
        )
        OutlinedTextField(
            value = gender.toString(),
            onValueChange = { gender = it.toIntOrNull() ?: 0 },
            label = { Text("Gender (0/1)") }
        )
        OutlinedTextField(
            value = bloodPressure.toString(),
            onValueChange = { bloodPressure = it.toIntOrNull() ?: 0 },
            label = { Text("Blood Pressure (0/1)") }
        )
        OutlinedTextField(
            value = cholesterolLevel.toString(),
            onValueChange = { cholesterolLevel = it.toIntOrNull() ?: 0 },
            label = { Text("Cholesterol Level (0/1)") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val symptoms = SymptomInput(
                fever = fever,
                cough = cough,
                fatigue = fatigue,
                difficulty_breathing = difficultyBreathing,
                age = age,
                gender = gender,
                blood_pressure = bloodPressure,
                cholesterol_level = cholesterolLevel
            )
            viewModel.predictDisease(symptoms)
        }) {
            Text("Predict Disease")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (viewModel.isLoading) {
            CircularProgressIndicator()
        } else if (viewModel.error.isNotEmpty()) {
            Text("Error: ${viewModel.error}", color = MaterialTheme.colorScheme.error)
        } else {
            Text("Predicted Disease: ${viewModel.predictedDisease}")
            Text("Treatment: ${viewModel.treatment}")
        }
    }
}