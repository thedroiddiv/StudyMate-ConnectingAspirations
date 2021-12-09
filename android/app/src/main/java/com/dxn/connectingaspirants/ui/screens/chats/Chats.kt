package com.dxn.connectingaspirants.ui.screens.chats

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.dxn.connectingaspirants.data.models.User
import com.dxn.connectingaspirants.ui.components.HeadingText
import com.dxn.connectingaspirants.ui.components.ListItem
import com.dxn.connectingaspirants.ui.components.TitleText
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@ExperimentalMaterialApi
@Composable
fun Chats(
    viewModel: ChatsViewModel,
    navController: NavController,
) {
    val users by remember { viewModel.users }

    LaunchedEffect(key1 = false, block = { viewModel.loadChats() })

    Scaffold(topBar = {
        TopAppBar(backgroundColor = MaterialTheme.colors.background) {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = ""
                    )
                }
                HeadingText(text = "Chats")
                IconButton(onClick = {

                }) {
                    Icon(
                        imageVector = Icons.Rounded.MoreVert,
                        contentDescription = ""
                    )
                }
            }
        }
    }
    ) {
        SwipeRefresh(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            state = rememberSwipeRefreshState(isRefreshing = false),
            indicator = { swipeState, trigger ->
                SwipeRefreshIndicator(
                    state = swipeState,
                    refreshTriggerDistance = trigger,
                    scale = true,
                )
            },
            onRefresh = { viewModel.loadChats() }
        ) {
            LazyColumn {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
                items(users) { user ->
                    ListItem(
                        modifier = Modifier.height(56.dp),
                        primaryText = user.name,
                        leadingIcon = {
                            Image(
                                modifier = Modifier.clip(CircleShape),
                                painter = rememberImagePainter(data = user.photoUrl),
                                contentDescription = "user icon"
                            )
                        }
                    ) {
                        navController.navigate("chat_screen/${user.uid}")
                    }
                }
            }
        }
    }


}