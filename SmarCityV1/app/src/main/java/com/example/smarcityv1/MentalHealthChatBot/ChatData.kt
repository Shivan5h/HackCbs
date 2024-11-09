package com.example.smarcityv1.MentalHealthChatBot

data class ChatData (
    val message : String ,
    val role : String
)

enum class ChatRoleEnum(val role : String)
{
    USER(role = "user"),
    MODEL(role = "model")
}