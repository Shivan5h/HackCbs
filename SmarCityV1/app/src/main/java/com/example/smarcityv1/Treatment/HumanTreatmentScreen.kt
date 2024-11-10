package com.example.smarcityv1.Treatment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.runBlocking
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST






@Composable
fun HumanTreatmentScreen() {
    var fever by remember { mutableStateOf("") }
    var cough by remember { mutableStateOf("") }
    var fatigue by remember { mutableStateOf("") }
    var difficultyBreathing by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var bloodPressure by remember { mutableStateOf("") }
    var cholesterolLevel by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    val retrofit = Retrofit.Builder()
        .baseUrl("https://3387ab99-c81c-4281-9e98-c00dda210e5f-00-3g7aaaapw29y5.picard.replit.dev/")
        .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().add(KotlinJsonAdapterFactory()).build()))
        .build()

    val apiService = retrofit.create(ApiService::class.java)

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(value = fever, onValueChange = { fever = it }, label = { Text("Fever (1/0)") })
        TextField(value = cough, onValueChange = { cough = it }, label = { Text("Cough (1/0)") })
        TextField(value = fatigue, onValueChange = { fatigue = it }, label = { Text("Fatigue (1/0)") })
        TextField(value = difficultyBreathing, onValueChange = { difficultyBreathing = it }, label = { Text("Difficulty Breathing (1/0)") })
        TextField(value = age, onValueChange = { age = it }, label = { Text("Age") })
        TextField(value = gender, onValueChange = { gender = it }, label = { Text("Gender (1/0)") })
        TextField(value = bloodPressure, onValueChange = { bloodPressure = it }, label = { Text("Blood Pressure (1/0)") })
        TextField(value = cholesterolLevel, onValueChange = { cholesterolLevel = it }, label = { Text("Cholesterol Level (1/0)") })
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val symptoms = SymptomInput(
                fever.toInt(),
                cough.toInt(),
                fatigue.toInt(),
                difficultyBreathing.toInt(),
                age.toInt(),
                gender.toInt(),
                bloodPressure.toInt(),
                cholesterolLevel.toInt()
            )
            result = runBlocking {
                try {
                    val response = apiService.predictDisease(symptoms)
                    "Disease: ${response.predicted_disease}, Treatment: ${response.treatment}"
                } catch (e: Exception) {
                    "Error: ${e.message}"
                }
            }
        }) {
            Text("Predict")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(result)
    }
}