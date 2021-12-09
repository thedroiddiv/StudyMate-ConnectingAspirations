package com.dxn.connectingaspirants.ui.screens.chats

import android.graphics.fonts.FontStyle
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.dxn.connectingaspirants.data.models.User
import com.dxn.connectingaspirants.ui.components.EditMessageField
import com.dxn.connectingaspirants.ui.components.HeadingText
import com.dxn.connectingaspirants.ui.components.MessageCard
import com.dxn.connectingaspirants.ui.components.TitleText

@Composable
fun Chat(
    receiverId: String,
    viewModel: ChatsViewModel,
    navController: NavController
) {
    var message by remember { mutableStateOf("") }
    val chat by remember { viewModel.chat }
    val sender by remember { viewModel.sender }
    val receiver by remember { viewModel.receiver }
    Scaffold(
        topBar = {
            TopAppBar(backgroundColor = MaterialTheme.colors.background) {
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = ""
                        )
                    }
                    HeadingText(text = receiver.name)
                    IconButton(onClick = {

                    }) {
                        Icon(
                            imageVector = Icons.Rounded.MoreVert,
                            contentDescription = ""
                        )
                    }
                }
            }
        },
        bottomBar = {
            EditMessageField(value = message, onChange = { message = it }) {
                viewModel.sendMessage(message.trim(), receiverId)
                message = ""
            }
        }
    ) {
        LazyColumn() {
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            items(chat.messages) { message ->
                if (message.senderId == sender.uid) {
                    MessageCard(
                        senderImage = sender.photoUrl,
                        senderName = sender.name,
                        message = message.text
                    )
                } else {
                    MessageCard(
                        senderImage = receiver.photoUrl,
                        senderName = receiver.name,
                        message = message.text
                    )
                }
            }
        }
    }

}