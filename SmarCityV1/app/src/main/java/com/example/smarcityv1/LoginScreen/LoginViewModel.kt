package com.example.smarcityv1.LoginScreen

// LoginViewModel.kt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    private val repository = LoginRepository()
    private val _loginStatus = MutableLiveData<LoginStatus>()
    val loginStatus: LiveData<LoginStatus> get() = _loginStatus

    fun login(email: String, password: String) {
        repository.login(email, password) { status ->
            _loginStatus.value = status
        }
    }
}
