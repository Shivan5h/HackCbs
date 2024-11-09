package com.example.smarcityv1.LoginScreen

// LoginRepository.kt
import com.google.firebase.auth.FirebaseAuth

class LoginRepository {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun login(email: String, password: String, callback: (LoginStatus) -> Unit) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(LoginStatus.Success)
                } else {
                    callback(LoginStatus.Failure(task.exception?.message ?: "Unknown Error"))
                }
            }
    }
}

sealed class LoginStatus {
    object Success : LoginStatus()
    data class Failure(val message: String) : LoginStatus()
}
