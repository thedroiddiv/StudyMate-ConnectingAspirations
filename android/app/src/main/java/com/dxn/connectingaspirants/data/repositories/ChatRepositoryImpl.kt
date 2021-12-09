package com.dxn.connectingaspirants.data.repositories

import android.util.Log
import com.dxn.connectingaspirants.data.models.Chat
import com.dxn.connectingaspirants.data.models.Message
import com.dxn.connectingaspirants.domain.repositories.ChatRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import com.dxn.connectingaspirants.common.Result
import com.dxn.connectingaspirants.data.models.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.onFailure


@ExperimentalCoroutinesApi
class ChatRepositoryImpl(
    val auth: FirebaseAuth,
    private val chatsCollection: CollectionReference,
    private val userCollection: CollectionReference
) : ChatRepository {

    override fun sendMessage(message: String, receiverId: String) {
        val sender = auth.uid!!
        val messageObj = Message(sender, receiverId, message)
        val chatId = getChatId(auth.uid!!, receiverId)
        chatsCollection.document(chatId).update("messages", FieldValue.arrayUnion(messageObj))
            .addOnSuccessListener {
                appendChat(sender, receiverId)
            }
            .addOnFailureListener { exception ->
                exception.localizedMessage?.let {
                    if (it.contains("NOT_FOUND")) {
                        chatsCollection.document(chatId)
                            .set(mapOf("messages" to listOf(messageObj)))
                        appendChat(sender, receiverId)
                    }
                }
            }
    }

    @ExperimentalCoroutinesApi
    override fun getChats(): Flow<Result<List<String>>> =
        callbackFlow {
            val listener = userCollection.document(auth.uid!!)
                .addSnapshotListener { value, firestoreException ->
                    trySend(Result.Loading())
                    if (firestoreException != null) {
                        cancel(
                            message = "error fetching collection data at path - $",
                            cause = firestoreException
                        )
                        firestoreException.printStackTrace()
                        trySend(
                            Result.Failure(
                                firestoreException.message ?: "something went wrong"
                            )
                        )
                        return@addSnapshotListener
                    }
                    trySend(Result.Success((value?.toObject(User::class.java))!!.chats))
                }
            awaitClose { listener.remove() }
        }

    @ExperimentalCoroutinesApi
    override fun getChat(receiverId: String): Flow<Result<Chat>> {
        return callbackFlow {
            val chatId = getChatId(auth.uid!!, receiverId)
            val listener = chatsCollection.document(chatId)
                .addSnapshotListener { value, exception ->
                    trySend(Result.Loading())
                    if (exception != null) {
                        cancel(
                            message = "error fetching collection data at path - $",
                            cause = exception
                        )
                        trySend(Result.Failure(exception.message ?: "something went wrong"))
                        return@addSnapshotListener
                    }
                    val chat = value?.toObject(Chat::class.java)
                    chat?.let {
                        trySend(Result.Success((chat)))
                    }
                    if (chat == null) {
                        chatsCollection.document(chatId).set(mapOf("messages" to listOf<Message>()))
                    }
                }
            awaitClose { listener.remove() }
        }
    }

    private fun appendChat(sender: String, receiver: String) {
        userCollection.document(sender)
            .update("chats", FieldValue.arrayUnion(receiver))
        userCollection.document(receiver)
            .update("chats", FieldValue.arrayUnion(sender))
    }

    private fun getChatId(senderId: String, receiverId: String) =
        if (senderId > receiverId) senderId + receiverId else receiverId + senderId

    companion object {
        const val TAG = "CHAT_REPOSITORY_IMPL"
    }

}