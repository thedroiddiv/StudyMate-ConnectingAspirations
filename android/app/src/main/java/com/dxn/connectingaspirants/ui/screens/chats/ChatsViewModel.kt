package com.dxn.connectingaspirants.ui.screens.chats

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dxn.connectingaspirants.common.Result
import com.dxn.connectingaspirants.data.models.Chat
import com.dxn.connectingaspirants.data.models.User
import com.dxn.connectingaspirants.domain.repositories.ChatRepository
import com.dxn.connectingaspirants.domain.repositories.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatsViewModel
@Inject
constructor(
    private val chatRepository: ChatRepository,
    private val firebaseRepository: FirebaseRepository,
    private val firebaseAuth: FirebaseAuth,
) : ViewModel() {

    val users = mutableStateOf(listOf<User>())

    init {
        loadChats()
    }
    fun loadChats() {
        chatRepository.getChats().onEach { result ->
            when (result) {
                is Result.Success -> {
                    result.data?.let { ids ->
                        Log.d(TAG, "loadChats: $ids")
                        users.value = firebaseRepository.getUsers(ids)
                    }
                }
                is Result.Failure -> {
                    Log.e(TAG, "loadChats: ${result.message}")
                }
                is Result.Loading -> {

                }
            }
        }.launchIn(viewModelScope)
    }
    companion object {
        const val TAG = "CHATS_VIEWMODEL"
    }


}