package com.dxn.connectingaspirants.ui.screens.chat

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.dxn.connectingaspirants.data.models.TargetParameters
import com.dxn.connectingaspirants.ui.components.EditMessageField
import com.dxn.connectingaspirants.ui.components.HeadingText
import com.dxn.connectingaspirants.ui.components.MessageCard

@Composable
fun Chat(
    receiverId: String,
    viewModel: ChatViewModel,
    navController: NavController
) {
    var message by remember { mutableStateOf("") }
    val chat by remember { viewModel.chat }
    val sender by remember { viewModel.sender }
    val receiver by remember { viewModel.receiver }
    var isDialogVisible by remember { mutableStateOf(false) }


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
                    TextButton(onClick = {
                        isDialogVisible = true
                    }) {
                        Text(text = "End Chat")
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
        LazyColumn {
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
        if (isDialogVisible) {
            val targetParameter = TargetParameters.getTargetParams(receiver.target)!!
            Dialog(onDismissRequest = { isDialogVisible = false }) {
                val selected =
                    remember { targetParameter.parameters.map { 0 }.toMutableStateList() }
                Card(
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        RatingScreen(
                            targetParameters = targetParameter,
                            selected = selected.toTypedArray(),
                            onSelect = { index, value ->
                                selected[index] = value
                            })
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                            TextButton(onClick = { isDialogVisible = false }) {
                                Text(text = "Dismiss")
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            TextButton(onClick = {
                                viewModel.submitRating(
                                    receiver.uid,
                                    selected.toTypedArray().average().toFloat()+1f
                                )
                                isDialogVisible=false
                                navController.popBackStack()

                            }) {
                                Text(text = "Submit")
                            }
                        }

                    }

                }

            }
        }
    }
}