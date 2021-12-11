package com.dxn.connectingaspirants.ui.screens

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dxn.connectingaspirants.ui.screens.chat.Chat
import com.dxn.connectingaspirants.ui.screens.chat.ChatViewModel
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
    val chatViewModel : ChatViewModel = viewModel()


    NavHost(navController = navController, startDestination = "home_screen") {
        composable("home_screen") {
            Home(
                viewModel = homeViewModel,
                currentUser = currentUser,
                navController = navController,
                signOut
            )
        }
        composable("chats_screen") {
            Chats(chatsViewModel, navController)
        }
        composable(
            "chat_screen/{receiverId}",
            arguments = listOf(navArgument("receiverId") {})
        ) { navBackStack ->
            val receiverId = navBackStack.arguments?.getString("receiverId")!!
            chatViewModel.loadChat(receiverId)
            chatViewModel.loadUsers(receiverId)
            Chat(receiverId = receiverId, viewModel = chatViewModel,navController)
        }
    }


}