package com.example.smarcityv1.MentalHealthChatBot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ChatBot(
    viewModel: ChatBotVM = viewModel()

){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        ChatHeader()

        Box(
            modifier = Modifier
                .weight(1f),
            contentAlignment = Alignment.Center
        ){
            if(viewModel.list.isEmpty())
            {
                Text(text = "No Chats Yet!")
            }
            else{
                ChatList(list = viewModel.list)}
        }

        ChatFooter{
            if(it.isNotEmpty()){

                viewModel.sendMessage(it)


            }

        }
    }
}