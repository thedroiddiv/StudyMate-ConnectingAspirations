package com.dxn.connectingaspirants.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.dxn.connectingaspirants.ui.screens.MainScreen
import com.dxn.connectingaspirants.ui.theme.ConnectingAspirantsTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConnectingAspirantsTheme {
                MainScreen(currentUser = firebaseAuth.currentUser!!)
            }
        }
    }
}
