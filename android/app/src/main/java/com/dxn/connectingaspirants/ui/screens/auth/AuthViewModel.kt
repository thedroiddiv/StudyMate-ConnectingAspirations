package com.dxn.connectingaspirants.ui.screens.auth


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.dxn.connectingaspirants.data.models.Level
import com.dxn.connectingaspirants.data.models.Target
import com.dxn.connectingaspirants.data.models.User
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

@HiltViewModel
class AuthViewModel
@Inject
constructor(
    private val fireStoreDb: FirebaseFirestore,
    private var auth: FirebaseAuth,
) : ViewModel() {

    val tags = listOf(
        Target.GATE, Target.JEE, Target.NEET, Target.PROGRAMMING, Target.UPSC
    )

    private val levels = listOf(
        Level.NEWBIE, Level.INTERMEDIATE, Level.EXPERIENCED
    )

    val selectedTagIndex = mutableStateOf(0)
    val selectedLevelIndex = mutableStateOf(0)
    val email = mutableStateOf("")
    val password = mutableStateOf("")

    fun createUser() {
        auth.currentUser?.let {
            val user = User(
                name = it.displayName!!,
                uid = auth.uid!!,
                photoUrl = it.photoUrl.toString(),
                target = tags[selectedTagIndex.value],
                level = levels[selectedLevelIndex.value]
            )
            fireStoreDb.collection("users_collection").document(auth.uid!!).set(user, SetOptions.mergeFields())
        }
    }


}