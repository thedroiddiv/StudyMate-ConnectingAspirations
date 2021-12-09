package com.dxn.connectingaspirants.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.dxn.connectingaspirants.data.models.User
import com.dxn.connectingaspirants.ui.components.ListItem
import com.dxn.connectingaspirants.ui.components.RoundedButton
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@ExperimentalMaterialApi
@Composable
fun Explore(
    currentUserId: String,
    users: List<User>,
    isRefreshing: Boolean,
    navController: NavController,
    refresh: () -> Unit,
) {
    SwipeRefresh(
        modifier = Modifier
            .fillMaxSize(),
        state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
        indicator = { swipeState, trigger ->
            SwipeRefreshIndicator(
                state = swipeState,
                refreshTriggerDistance = trigger,
                scale = true,
            )
        },
        onRefresh = refresh
    ) {
        LazyColumn(Modifier.padding(horizontal = 16.dp)) {
            item {
                Text(
                    modifier = Modifier.padding(vertical = 24.dp),
                    text = "Amazing people are here!"
                )
            }
            items(users) { user ->
                if (user.uid != currentUserId)
                    ListItem(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .height(52.dp)
                            .background(Color.Transparent),
                        leadingIcon = {
                            Image(
                                modifier = Modifier.clip(CircleShape),
                                painter = rememberImagePainter(data = user.photoUrl),
                                contentDescription = "user icon"
                            )
                        },
                        trailingIcon = {
                            RoundedButton(
                                modifier = Modifier.scale(0.7f),
                                text = "Connect",
                                textPadding = 2.dp
                            ) {
                                navController.navigate("chat_screen/${user.uid}")
                            }
                        },
                        primaryText = user.name
                    )
            }
        }
    }
}