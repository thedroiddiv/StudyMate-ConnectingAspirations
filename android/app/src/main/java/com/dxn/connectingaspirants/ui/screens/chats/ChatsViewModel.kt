package com.dxn.connectingaspirants.ui.screens.chats

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dxn.connectingaspirants.data.models.Chat
import com.dxn.connectingaspirants.domain.repositories.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import com.dxn.connectingaspirants.common.Result
import com.dxn.connectingaspirants.data.models.Message
import com.dxn.connectingaspirants.data.models.User
import com.dxn.connectingaspirants.domain.repositories.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth

@HiltViewModel
class ChatsViewModel
@Inject
constructor(
    private val chatRepository: ChatRepository,
    private val firebaseRepository: FirebaseRepository,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    val chats = mutableStateOf(listOf<String>())
    val chat = mutableStateOf(Chat())

    fun loadChats() {
        chatRepository.getChats().onEach { result ->
            when (result) {
                is Result.Success -> {
                    result.data?.let {
                        chats.value = it
                    }
                }
                is Result.Failure -> {
                    Log.d(TAG, "loadChats: ${result.message}")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun loadUsers() {

    }


    fun sendMessage(message: String, receiverId: String) {
        chatRepository.sendMessage(message, receiverId)
    }

    fun loadChat(receiverId: String) {
        Log.d(TAG, "loadChats: ${"message"}")
        chatRepository.getChat(receiverId).onEach { result ->
            Log.d(TAG, "loadChats: ${result.message}")
            when (result) {
                is Result.Success -> {
                    result.data?.let {
                        chat.value = it
                    }
                }
                is Result.Failure -> {
                    Log.d(TAG, "loadChats: ${result.message}")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getUser(userId: String) {

    }

    companion object {
        const val TAG = "CHATS_VIEWMODEL"
    }

}