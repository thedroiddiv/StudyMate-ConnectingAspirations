package com.dxn.connectingaspirants.domain.repositories

import com.dxn.connectingaspirants.common.Result
import com.dxn.connectingaspirants.data.models.User
import kotlinx.coroutines.flow.Flow


interface FirebaseRepository {
    fun getUsers(): Flow<Result<List<User>>>
    fun getUser(userId: String): Flow<Result<User>>
}