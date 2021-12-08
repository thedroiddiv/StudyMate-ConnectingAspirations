package com.dxn.connectingaspirants.ui.screens

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dxn.connectingaspirants.App
import com.dxn.connectingaspirants.ui.screens.chats.Chats
import com.dxn.connectingaspirants.ui.screens.home.Home
import com.dxn.connectingaspirants.ui.screens.home.HomeViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.firebase.auth.FirebaseUser

@ExperimentalPagerApi
@Composable
fun MainScreen(
    currentUser:FirebaseUser
) {

    val navController = rememberNavController()
    val homeViewModel : HomeViewModel = viewModel()
    NavHost(navController = navController, startDestination = "home_screen") {
        composable("home_screen") {
            Home(viewModel = homeViewModel, currentUser = currentUser,navController=navController)
        }
        composable("chats_screen"){
            Chats()
        }
    }

}