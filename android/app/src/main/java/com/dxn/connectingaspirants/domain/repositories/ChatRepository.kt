package com.dxn.connectingaspirants.domain.repositories

import com.dxn.connectingaspirants.common.Result
import com.dxn.connectingaspirants.data.models.Chat
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun sendMessage(message: String, receiverId: String)
    fun getChats(): Flow<Result<List<String>>>
    fun getChat(receiverId: String): Flow<Result<Chat>>
}