package com.dxn.connectingaspirants.ui.screens.chats

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dxn.connectingaspirants.data.models.User
import com.dxn.connectingaspirants.ui.components.ListItem
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@ExperimentalMaterialApi
@Composable
fun Chats(
    viewModel: ChatsViewModel,
    navController: NavController,
    user: User
) {
    val chats by remember { viewModel.chats }

    LaunchedEffect(key1 = false, block = {
        viewModel.loadChats(user.chats)
    })

    SwipeRefresh(
        modifier = Modifier.fillMaxSize(),
        state = rememberSwipeRefreshState(isRefreshing = false),
        indicator = { swipeState, trigger ->
            SwipeRefreshIndicator(
                state = swipeState,
                refreshTriggerDistance = trigger,
                scale = true,
            )
        },
        onRefresh = { viewModel.loadChats(user.chats) }
    ) {
        LazyColumn {
            items(chats) { chat ->
                ListItem(
                    modifier = Modifier.height(56.dp),
                    primaryText = chat
                ) {
                    navController.navigate("chat_screen/$chat")
                }
            }
        }
    }
}