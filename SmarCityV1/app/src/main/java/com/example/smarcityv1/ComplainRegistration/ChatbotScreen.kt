package com.example.smarcityv1.ComplainRegistration

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Alignment


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatbotScreen(viewModel: MainViewModel) {
    var userInput by remember { mutableStateOf("") }
    var response by remember { mutableStateOf("") }
    var isServiceRequest by remember { mutableStateOf(true) }
    var selectedCategory by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf("") }
    var writtenDetails by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Complain Chat Bot") }
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(16.dp)
                ) {
                    if (response.isEmpty()) {
                        Text("Do you want to request a service or raise a complaint?")
                        Spacer(modifier = Modifier.height(16.dp))
                        Row {
                            Button(onClick = { isServiceRequest = true; response = "Please provide the details of the service you need." }) {
                                Text("Request Service")
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(onClick = { isServiceRequest = false; response = "Please select a category: Electricity, Water, Waste, Road, Others" }) {
                                Text("Raise Complaint")
                            }
                        }
                    } else if (!isServiceRequest && selectedCategory.isEmpty()) {
                        Text(response)
                        Spacer(modifier = Modifier.height(16.dp))
                        Column {
                            Button(onClick = { selectedCategory = "Electricity"; response = "Please select a type: Street Lightening, Frequent Cuts, Frequent Low Voltage" }) {
                                Text("Electricity")
                            }
                            Button(onClick = { selectedCategory = "Water"; response = "Please select a type: Poor Drainage, Poor Quality Water, No availability of water" }) {
                                Text("Water")
                            }
                            Button(onClick = { selectedCategory = "Waste"; response = "Please select a type: Lack of Green Space, Sewage Issues, Waste collection service Issues, Waste accumulated at a place" }) {
                                Text("Waste")
                            }
                            Button(onClick = { selectedCategory = "Road"; response = "Please select a type: Potholes, Maintenance of water, Sidewalk accessibility, Traffic congestion, Public transport" }) {
                                Text("Road")
                            }
                            Button(onClick = { selectedCategory = "Others"; response = "Please select a type: Public Safety, School Accessibility, Healthcare Services, Public Property Maintenance, Internet Connectivity, Air Quality, Noise Pollution, or type your own" }) {
                                Text("Others")
                            }
                        }
                    } else if (!isServiceRequest && selectedType.isEmpty()) {
                        Text(response)
                        Spacer(modifier = Modifier.height(16.dp))
                        Column {
                            when (selectedCategory) {
                                "Electricity" -> {
                                    Button(onClick = { selectedType = "Street Lightening"; response = "Please provide the details of your complaint." }) {
                                        Text("Street Lightening")
                                    }
                                    Button(onClick = { selectedType = "Frequent Cuts"; response = "Please provide the details of your complaint." }) {
                                        Text("Frequent Cuts")
                                    }
                                    Button(onClick = { selectedType = "Frequent Low Voltage"; response = "Please provide the details of your complaint." }) {
                                        Text("Frequent Low Voltage")
                                    }
                                }
                                "Water" -> {
                                    Button(onClick = { selectedType = "Poor Drainage"; response = "Please provide the details of your complaint." }) {
                                        Text("Poor Drainage")
                                    }
                                    Button(onClick = { selectedType = "Poor Quality Water"; response = "Please provide the details of your complaint." }) {
                                        Text("Poor Quality Water")
                                    }
                                    Button(onClick = { selectedType = "No availability of water"; response = "Please provide the details of your complaint." }) {
                                        Text("No availability of water")
                                    }
                                }
                                "Waste" -> {
                                    Button(onClick = { selectedType = "Lack of Green Space"; response = "Please provide the details of your complaint." }) {
                                        Text("Lack of Green Space")
                                    }
                                    Button(onClick = { selectedType = "Sewage Issues"; response = "Please provide the details of your complaint." }) {
                                        Text("Sewage Issues")
                                    }
                                    Button(onClick = { selectedType = "Waste collection service Issues"; response = "Please provide the details of your complaint." }) {
                                        Text("Waste collection service Issues")
                                    }
                                    Button(onClick = { selectedType = "Waste accumulated at a place"; response = "Please provide the details of your complaint." }) {
                                        Text("Waste accumulated at a place")
                                    }
                                }
                                "Road" -> {
                                    Button(onClick = { selectedType = "Potholes"; response = "Please provide the details of your complaint." }) {
                                        Text("Potholes")
                                    }
                                    Button(onClick = { selectedType = "Maintenance of water"; response = "Please provide the details of your complaint." }) {
                                        Text("Maintenance of water")
                                    }
                                    Button(onClick = { selectedType = "Sidewalk accessibility"; response = "Please provide the details of your complaint." }) {
                                        Text("Sidewalk accessibility")
                                    }
                                    Button(onClick = { selectedType = "Traffic congestion"; response = "Please provide the details of your complaint." }) {
                                        Text("Traffic congestion")
                                    }
                                    Button(onClick = { selectedType = "Public transport"; response = "Please provide the details of your complaint." }) {
                                        Text("Public transport")
                                    }
                                }
                                "Others" -> {
                                    Button(onClick = { selectedType = "Public Safety"; response = "Please provide the details of your complaint." }) {
                                        Text("Public Safety")
                                    }
                                    Button(onClick = { selectedType = "School Accessibility"; response = "Please provide the details of your complaint." }) {
                                        Text("School Accessibility")
                                    }
                                    Button(onClick = { selectedType = "Healthcare Services"; response = "Please provide the details of your complaint." }) {
                                        Text("Healthcare Services")
                                    }
                                    Button(onClick = { selectedType = "Public Property Maintenance"; response = "Please provide the details of your complaint." }) {
                                        Text("Public Property Maintenance")
                                    }
                                    Button(onClick = { selectedType = "Internet Connectivity"; response = "Please provide the details of your complaint." }) {
                                        Text("Internet Connectivity")
                                    }
                                    Button(onClick = { selectedType = "Air Quality"; response = "Please provide the details of your complaint." }) {
                                        Text("Air Quality")
                                    }
                                    Button(onClick = { selectedType = "Noise Pollution"; response = "Please provide the details of your complaint." }) {
                                        Text("Noise Pollution")
                                    }
                                    TextField(
                                        value = selectedType,
                                        onValueChange = { selectedType = it },
                                        label = { Text("Type your own") },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }
                    } else {
                        Text(response)
                        Spacer(modifier = Modifier.height(16.dp))
                        TextField(
                            value = writtenDetails,
                            onValueChange = { writtenDetails = it },
                            label = { Text("Please provide the details of your complaint.") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = {
                            if (isServiceRequest) {
                                viewModel.requestService(writtenDetails)
                            } else {
                                viewModel.raiseComplaint(selectedCategory, selectedType, writtenDetails)
                            }
                            response = "Your request has been submitted."
                        }) {
                            Text("Submit")
                        }
                    }
                }
            }
        }
    )
}