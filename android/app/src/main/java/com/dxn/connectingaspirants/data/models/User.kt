package com.dxn.connectingaspirants.data.models

data class User(
    val name: String = "",
    val uid: String = "",
    val photoUrl: String = "",
    val level: Level = Level.NEWBIE,
    val target: Target = Target.JEE,
    val rating: Float = 5.0f,
    val chats: List<String> = listOf()  // uid of users with whom the chat is
)