package com.dxn.connectingaspirants.data.repositories

import android.util.Log
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
    override fun getUsers(): Flow<Result<List<User>>> = callbackFlow {
        val listener = userCollection.addSnapshotListener { value, firestoreException ->
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

    @ExperimentalCoroutinesApi
    override fun getUser(userId: String): Flow<Result<User>> = callbackFlow {
        val listener = userCollection.document(userId).addSnapshotListener { value, firestoreException ->
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
            trySend(Result.Success((value?.toObject(User::class.java))!!))
        }
        awaitClose { listener.remove() }
    }


    companion object {
        const val TAG = "FIREBASE_REPOSITORY_IMPL"
    }
}