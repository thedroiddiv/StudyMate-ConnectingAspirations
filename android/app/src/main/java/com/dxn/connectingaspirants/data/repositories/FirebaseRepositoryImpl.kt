package com.dxn.connectingaspirants.data.repositories

import com.dxn.connectingaspirants.common.Result
import com.dxn.connectingaspirants.data.models.Chat
import com.dxn.connectingaspirants.data.models.User
import com.dxn.connectingaspirants.domain.repositories.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FirebaseRepositoryImpl(
    private val auth: FirebaseAuth,
    private val userCollection: CollectionReference
) : FirebaseRepository {

    @ExperimentalCoroutinesApi
    override suspend fun getChats(): Flow<Result<List<Chat>>> = callbackFlow {
        val listener = userCollection.document(auth.uid!!).collection("chats")
            .addSnapshotListener { value, firestoreException ->
                trySend(Result.Loading())
                if (firestoreException != null) {
                    cancel(
                        message = "error fetching collection data at path - $",
                        cause = firestoreException
                    )
                    firestoreException.printStackTrace()
                    trySend(Result.Failure(firestoreException.message ?: "something went wrong"))
                    return@addSnapshotListener
                }
                trySend(Result.Success((value?.toObjects(Chat::class.java))!!))
            }
        awaitClose { listener.remove() }
    }

    @ExperimentalCoroutinesApi
    override suspend fun getChat(chatId: String): Flow<Result<Chat>> = callbackFlow {
        val listener = userCollection.document(auth.uid!!).collection("chats").document(chatId)
            .addSnapshotListener { value, firestoreException ->
                trySend(Result.Loading())
                if (firestoreException != null) {
                    cancel(
                        message = "error fetching collection data at path - $",
                        cause = firestoreException
                    )
                    firestoreException.printStackTrace()
                    trySend(Result.Failure(firestoreException.message ?: "something went wrong"))
                    return@addSnapshotListener
                }
                trySend(Result.Success((value?.toObject(Chat::class.java))!!))
            }
        awaitClose { listener.remove() }
    }

    override suspend fun getUsers(): Flow<Result<List<User>>> = callbackFlow {
        val listener = userCollection.document(auth.uid!!).collection("chats")
            .addSnapshotListener { value, firestoreException ->
                trySend(Result.Loading())
                if (firestoreException != null) {
                    cancel(
                        message = "error fetching collection data at path - $",
                        cause = firestoreException
                    )
                    firestoreException.printStackTrace()
                    trySend(Result.Failure(firestoreException.message ?: "something went wrong"))
                    return@addSnapshotListener
                }
                trySend(Result.Success((value?.toObjects(User::class.java))!!))
            }
        awaitClose { listener.remove() }
    }
}