package com.dxn.connectingaspirants.data.models

data class Message(
    val senderId: String="",
    val receiverId: String="",
    val text: String="",
    val timeStamp: Long = System.currentTimeMillis()
)