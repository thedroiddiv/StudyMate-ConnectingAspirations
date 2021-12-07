package com.dxn.connectingaspirants.data.models

data class Chat(
    val senderId : String,
    val receiverId : String,
    val messages: List<Message>
)