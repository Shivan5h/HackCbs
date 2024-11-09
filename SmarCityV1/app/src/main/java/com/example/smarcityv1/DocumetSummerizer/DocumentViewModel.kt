package com.example.smarcityv1.DocumetSummerizer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SimplifyViewModel : ViewModel() {
    val simplifiedText = MutableLiveData<String>()
    val error = MutableLiveData<String>()

    fun simplifyText(legalText: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.simplifyLegalText(LegalTextRequest(legalText))
                simplifiedText.value = response.simplified_text
            } catch (e: Exception) {
                error.value = e.message
            }
        }
    }
}