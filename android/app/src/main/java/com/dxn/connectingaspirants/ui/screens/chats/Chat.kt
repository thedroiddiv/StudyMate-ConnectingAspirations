package com.dxn.connectingaspirants.ui.screens.chats

import android.graphics.fonts.FontStyle
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dxn.connectingaspirants.data.models.User

@Composable
fun Chat(
    receiverId: String,
    viewModel: ChatsViewModel,
) {
    var message by remember { mutableStateOf("") }
    viewModel.loadChat(receiverId)
    val chat by remember { viewModel.chat }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Chat",
                        style = TextStyle(
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    )
                },
                actions = {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        modifier = Modifier.padding(start = 8.dp, end = 10.dp),
                        contentDescription = ""
                    )
                    Icon(
                        imageVector = Icons.Rounded.MoreVert,
                        modifier = Modifier.padding(start = 8.dp, end = 10.dp),
                        contentDescription = ""
                    )
                },
                backgroundColor = Color.White
            )
        },
        bottomBar = {
            Row(Modifier.fillMaxWidth()) {
                TextField(value = message, onValueChange = { message = it })
                IconButton(onClick = { viewModel.sendMessage(message, receiverId) }) {
                    Icon(imageVector = Icons.Rounded.Send, contentDescription = "send")
                }
            }
        }
    ) {
        LazyColumn() {
            items(chat.messages) { message ->
                Text(text = message.text)
            }
        }
    }

}