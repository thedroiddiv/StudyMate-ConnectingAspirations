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
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.launch
import kotlin.math.log

@HiltViewModel
class ChatsViewModel
@Inject
constructor(
    private val chatRepository: ChatRepository,
    private val firebaseRepository: FirebaseRepository,
    private val firebaseAuth: FirebaseAuth,
) : ViewModel() {

    val users = mutableStateOf(listOf<User>())
    val chat = mutableStateOf(Chat())
    val sender = mutableStateOf(User())
    val receiver = mutableStateOf(User())
    val senderId = firebaseAuth.uid!!

    init {
        loadChats()
    }

    fun loadUsers(receiverId: String) {
        viewModelScope.launch {
            when(val senderResult  = firebaseRepository.getUser(senderId)) {
                is Result.Success -> { sender.value = senderResult.data!! }
                is Result.Failure -> { Log.e(TAG, "loadChats: ${senderResult.message}") }
                is Result.Loading -> { }
            }
            when(val receiverResult  = firebaseRepository.getUser(receiverId)) {
                is Result.Success -> { receiver.value = receiverResult.data!! }
                is Result.Failure -> { Log.e(TAG, "loadChats: ${receiverResult.message}") }
                is Result.Loading -> { }
            }
        }
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
                    Log.e(TAG, "loadChats: ${result.message}")
                }
            }
        }.launchIn(viewModelScope)
    }

    companion object {
        const val TAG = "CHATS_VIEWMODEL"
    }

}