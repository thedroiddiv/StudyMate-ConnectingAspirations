package com.dxn.connectingaspirants.data.models

import com.google.firebase.Timestamp
import com.google.firebase.firestore.ServerTimestamp
import kotlin.math.roundToInt

data class User(
    val name: String = "",
    val uid: String = "",
    val photoUrl: String = "",
    val level: Level = Level.NEWBIE,
    val target: Target = Target.JEE,
    val rating: Float = 0.0f,
    val ratings: List<Rating> = listOf(), // final rating of a user long -> timestamp
    val chats: List<String> = listOf()    // uid of users with whom the chat is
)

data class Rating(
    val value: Float = 0f,
    @ServerTimestamp val timeStamp: Timestamp = Timestamp.now()
)

fun getAverageRating(ratings: List<Rating>): Float {
    val number = (ratings.map { it.value }.average())
    return ((number * 100.0).roundToInt() / 100.0).toFloat()
}