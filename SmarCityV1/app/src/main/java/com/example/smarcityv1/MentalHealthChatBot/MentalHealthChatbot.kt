package com.example.smarcityv1.MentalHealthChatBot

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.launch

class ChatBotVM(private val apiKey: String) : ViewModel() {
    val list by lazy {
        mutableStateListOf<ChatData>()
    }

    private val genAI by lazy {
        GenerativeModel(
            modelName = "gemini-pro",
            apiKey = "your api key"
        )
    }

    fun sendMessage(message: String) = viewModelScope.launch {
        try {
            val chat = genAI.startChat()

            // Add user message to the chat list
            list.add(ChatData(message, ChatRoleEnum.USER.role))

            // Customize the prompt for mental health support
            val prompt = "You are a mental health support chatbot. Provide compassionate and helpful responses to the user's concerns. User: $message"

            // Send the customized prompt to the model
            chat.sendMessage(
                content(ChatRoleEnum.USER.role) { text(prompt) }
            ).text?.let {
                // Add model response to the chat list
                list.add(ChatData(it, ChatRoleEnum.MODEL.role))
            }
        } catch (e: Exception) {
            // Handle the error appropriately
            list.add(ChatData("Error: ${e.message}", ChatRoleEnum.MODEL.role))
        }
    }
}