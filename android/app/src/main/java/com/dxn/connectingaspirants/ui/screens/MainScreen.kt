package com.dxn.connectingaspirants.ui.screens

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dxn.connectingaspirants.ui.screens.chats.Chat
import com.dxn.connectingaspirants.ui.screens.chats.Chats
import com.dxn.connectingaspirants.ui.screens.chats.ChatsViewModel
import com.dxn.connectingaspirants.ui.screens.home.Home
import com.dxn.connectingaspirants.ui.screens.home.HomeViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.firebase.auth.FirebaseUser

@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun MainScreen(
    currentUser: FirebaseUser,
    signOut: () -> Unit
) {

    val navController = rememberNavController()
    val homeViewModel: HomeViewModel = viewModel()
    val chatsViewModel: ChatsViewModel = viewModel()

    val user by remember { homeViewModel.user }

    NavHost(navController = navController, startDestination = "home_screen") {
        composable("home_screen") {
            Home(
                viewModel = homeViewModel,
                currentUser = currentUser,
                navController = navController
            )
        }
        composable("chats_screen") {
            Chats(chatsViewModel, navController, user!!)
        }
        composable(
            "chat_screen/{receiverId}",
            arguments = listOf(navArgument("receiverId") {})
        ) { navBackStack ->
            val receiver = navBackStack.arguments?.getString("receiverId")
            Chat(receiverId = receiver!!, viewModel = chatsViewModel)
        }
    }

}