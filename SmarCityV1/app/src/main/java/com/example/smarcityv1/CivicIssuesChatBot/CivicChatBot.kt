package com.example.smarcityv1.CivicIssuesChatBot

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smarcityv1.MentalHealthChatBot.ChatList


@Composable
fun CivicChatBot(
    viewModel: CivicChatBotVM = viewModel()

){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        CivicChatHeader()

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