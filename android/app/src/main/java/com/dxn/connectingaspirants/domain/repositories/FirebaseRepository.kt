package com.dxn.connectingaspirants.domain.repositories

import com.dxn.connectingaspirants.data.models.Chat
import com.dxn.connectingaspirants.data.models.User
import kotlinx.coroutines.flow.Flow
import com.dxn.connectingaspirants.common.Result


interface FirebaseRepository {
    suspend fun getChats(): Flow<Result<List<Chat>>>
    suspend fun getChat(chatId: String): Flow<Result<Chat>>
    suspend fun getUsers(): Flow<Result<List<User>>>
}