package com.dxn.connectingaspirants.data.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp

data class Message(
    val senderId: String="",
    val receiverId: String="",
    val text: String="",
    @ServerTimestamp val timeStamp: Timestamp = Timestamp.now()
)