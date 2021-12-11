package com.dxn.connectingaspirants.ui.screens.chat

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dxn.connectingaspirants.common.Result
import com.dxn.connectingaspirants.data.models.Chat
import com.dxn.connectingaspirants.data.models.User
import com.dxn.connectingaspirants.domain.repositories.ChatRepository
import com.dxn.connectingaspirants.domain.repositories.FirebaseRepository
import com.dxn.connectingaspirants.ui.screens.chats.ChatsViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ChatViewModel
@Inject
constructor(
    private val chatRepository: ChatRepository,
    private val firebaseRepository: FirebaseRepository,
    firebaseAuth: FirebaseAuth
) : ViewModel() {
    val chat = mutableStateOf(Chat())
    val sender = mutableStateOf(User())
    val receiver = mutableStateOf(User())
    private val senderId = firebaseAuth.uid!!
    fun loadUsers(receiverId: String) {
        viewModelScope.launch {
            when (val senderResult = firebaseRepository.getUser(senderId)) {
                is Result.Success -> {
                    sender.value = senderResult.data!!
                }
                is Result.Failure -> {
                    Log.e(ChatsViewModel.TAG, "loadChats: ${senderResult.message}")
                }
                is Result.Loading -> {
                }
            }
            when (val receiverResult = firebaseRepository.getUser(receiverId)) {
                is Result.Success -> {
                    receiver.value = receiverResult.data!!

                }
                is Result.Failure -> {
                    Log.e(ChatsViewModel.TAG, "loadChats: ${receiverResult.message}")
                }
                is Result.Loading -> {
                }
            }
        }
    }

    fun sendMessage(message: String, receiverId: String) {
        chatRepository.sendMessage(message, receiverId)
    }

    fun loadChat(receiverId: String) {
        chatRepository.getChat(receiverId).onEach { result ->
            when (result) {
                is Result.Success -> {
                    result.data?.let { chat.value = it }
                }
                is Result.Failure -> {
                    Log.e(ChatsViewModel.TAG, "loadChats: ${result.message}")
                }
                is Result.Loading -> {

                }
            }
        }.launchIn(viewModelScope)
    }

    fun submitRating(receiverId: String,rating:Float) {
        viewModelScope.launch {
            firebaseRepository.updateRating(userId=receiverId,rating)
        }
    }
}