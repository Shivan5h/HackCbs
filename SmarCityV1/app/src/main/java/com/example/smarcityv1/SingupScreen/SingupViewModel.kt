package com.example.smarcityv1.SingupScreen

// SignUpViewModel.kt
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State

class SignUpViewModel : ViewModel() {
    private val _firstName = mutableStateOf("")
    val firstName: State<String> = _firstName

    private val _lastName = mutableStateOf("")
    val lastName: State<String> = _lastName

    private val _dateOfBirth = mutableStateOf("")
    val dateOfBirth: State<String> = _dateOfBirth

    private val _gender = mutableStateOf("")
    val gender: State<String> = _gender

    fun onFirstNameChange(newName: String) {
        _firstName.value = newName
    }

    fun onLastNameChange(newName: String) {
        _lastName.value = newName
    }

    fun onDateOfBirthChange(newDate: String) {
        _dateOfBirth.value = newDate
    }

    fun onGenderChange(newGender: String) {
        _gender.value = newGender
    }
}
