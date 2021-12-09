package com.dxn.connectingaspirants.ui.screens.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dxn.connectingaspirants.common.Result
import com.dxn.connectingaspirants.data.models.User
import com.dxn.connectingaspirants.domain.repositories.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    private val firebaseRepository: FirebaseRepository,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _users = mutableStateOf(listOf<User>())
    val users: State<List<User>> = _users

    val user: MutableState<User> = mutableStateOf(User())

    val error = mutableStateOf("")
    val isLoading = mutableStateOf(false)

    init {
        loadUsers()
        loadUser()
    }

    fun loadUsers() {
        firebaseRepository.getUsers().onEach { result ->
            when (result) {
                is Result.Loading -> {
                    isLoading.value = true
                }
                is Result.Success -> {
                    _users.value = result.data!!
                    isLoading.value = false
                }
                is Result.Failure -> {
                    result.message?.let { error.value = it }
                    isLoading.value = false
                }
            }
        }.launchIn(viewModelScope)
    }

    fun loadUser() {
        viewModelScope.launch {
            firebaseRepository.getUser(firebaseAuth.uid!!).let {
                when (it) {
                    is Result.Success -> {
                        user.value = it.data!!
                        isLoading.value=false
                    }
                    is Result.Failure -> {
                        Log.d(TAG, "loadUser: ${it.message}")
                        isLoading.value=false
                    }
                    is Result.Loading -> {
                        isLoading.value=true
                    }
                }
            }
        }

    }

    companion object {
        const val TAG = "HOME_VIEWMODEL"
    }
}