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

data class TreatmentInput(
    val animal: String,
    val symptoms: List<String>
)

data class TreatmentResponse(
    val animal: String,
    val symptoms: List<String>,
    val predicted_treatment: String
)

interface AnimalApiService {
    @POST("/predict_treatment/")
    suspend fun predictTreatment(@Body input: TreatmentInput): TreatmentResponse
}



@Composable
fun AnimalTreatmentScreen() {
    var animal by remember { mutableStateOf("") }
    var symptom1 by remember { mutableStateOf("") }
    var symptom2 by remember { mutableStateOf("") }
    var symptom3 by remember { mutableStateOf("") }
    var symptom4 by remember { mutableStateOf("") }
    var symptom5 by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    val retrofit = Retrofit.Builder()
        .baseUrl("https://3387ab99-c81c-4281-9e98-c00dda210e5f-00-3g7aaaapw29y5.picard.replit.dev/")
        .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().add(KotlinJsonAdapterFactory()).build()))
        .build()

    val apiService = retrofit.create(AnimalApiService::class.java)

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(value = animal, onValueChange = { animal = it }, label = { Text("Animal Name") })
        TextField(value = symptom1, onValueChange = { symptom1 = it }, label = { Text("Symptom 1") })
        TextField(value = symptom2, onValueChange = { symptom2 = it }, label = { Text("Symptom 2") })
        TextField(value = symptom3, onValueChange = { symptom3 = it }, label = { Text("Symptom 3") })
        TextField(value = symptom4, onValueChange = { symptom4 = it }, label = { Text("Symptom 4") })
        TextField(value = symptom5, onValueChange = { symptom5 = it }, label = { Text("Symptom 5") })
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val symptoms = listOf(symptom1, symptom2, symptom3, symptom4, symptom5)
            val input = TreatmentInput(animal, symptoms)
            result = runBlocking {
                try {
                    val response = apiService.predictTreatment(input)
                    "Animal: ${response.animal}, Symptoms: ${response.symptoms.joinToString()}, Treatment: ${response.predicted_treatment}"
                } catch (e: Exception) {
                    "Error: ${e.message}"
                }
            }
        }) {
            Text("Predict Treatment")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(result)
    }
}