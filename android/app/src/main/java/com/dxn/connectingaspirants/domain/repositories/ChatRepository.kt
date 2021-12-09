package com.dxn.connectingaspirants.domain.repositories

import com.google.firebase.firestore.DocumentReference
import kotlinx.coroutines.flow.Flow
import com.dxn.connectingaspirants.common.Result
import com.dxn.connectingaspirants.data.models.Chat
import com.dxn.connectingaspirants.data.models.User
import kotlinx.coroutines.ExperimentalCoroutinesApi

interface ChatRepository {
    fun sendMessage(message: String, receiverId: String)
    fun getChats(): Flow<Result<List<String>>>
    fun getChat(receiverId: String): Flow<Result<Chat>>
}