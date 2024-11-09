package com.example.smarcityv1.ComplainRegistration

data class ServiceRequest(
    val name: String,
    val address: String,
    val phoneNumber: String,
    val email: String,
    val writtenDetails: String
)

data class ComplaintRequest(
    val name: String,
    val address: String,
    val phoneNumber: String,
    val email: String,
    val category: String,
    val type: String,
    val writtenDetails: String
)