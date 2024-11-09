package com.example.smarcityv1.CivicIssuesChatBot

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarcityv1.MentalHealthChatBot.ChatData
import com.example.smarcityv1.MentalHealthChatBot.ChatRoleEnum
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.launch

class CivicChatBotVM(private val apiKey: String) : ViewModel() {
    val list by lazy {
        mutableStateListOf<ChatData>()
    }

    private val genAI by lazy {
        GenerativeModel(
            modelName = "gemini-pro",
            apiKey = "Your API key"
        )
    }

    fun sendMessage(message: String) = viewModelScope.launch {
        try {
            val chat = genAI.startChat()

            // Add user message to the chat list
            list.add(ChatData(message, ChatRoleEnum.USER.role))

            // Customize the prompt for civic issues
            val prompt = "You are a civic issues chatbot. Help the user with their civic issues. User: $message"

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