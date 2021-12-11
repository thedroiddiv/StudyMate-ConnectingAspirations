package com.dxn.connectingaspirants.domain.repositories

import com.dxn.connectingaspirants.common.Result
import com.dxn.connectingaspirants.data.models.User
import kotlinx.coroutines.flow.Flow


interface FirebaseRepository {
    fun getUsers(): Flow<Result<List<User>>>
    suspend fun getUser(userId: String): Result<User>
    suspend fun getUsers(userIds:List<String>) : List<User>
    suspend fun updateRating(userId: String,rating:Float)
}