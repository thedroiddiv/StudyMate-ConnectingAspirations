package com.dxn.connectingaspirants.ui.screens

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import com.dxn.connectingaspirants.domain.repositories.ChatRepository
import com.dxn.connectingaspirants.ui.screens.MainScreen
import com.dxn.connectingaspirants.ui.screens.auth.SignInActivity
import com.dxn.connectingaspirants.ui.theme.ConnectingAspirantsTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var chatRepository: ChatRepository

    @ExperimentalMaterialApi
    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConnectingAspirantsTheme {
                MainScreen(currentUser = firebaseAuth.currentUser!!, signOut = signOut)
            }
        }
    }

    private val signOut: () -> Unit = {
        firebaseAuth.signOut()
        startActivity(Intent(this, SignInActivity::class.java))
        finish()
    }
}
