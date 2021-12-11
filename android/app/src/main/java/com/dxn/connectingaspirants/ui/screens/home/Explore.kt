package com.dxn.connectingaspirants.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.dxn.connectingaspirants.data.models.Target
import com.dxn.connectingaspirants.data.models.User
import com.dxn.connectingaspirants.data.models.getAverageRating
import com.dxn.connectingaspirants.ui.components.*
import com.dxn.connectingaspirants.ui.screens.profile.Profile
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

    var selectedIndex by remember { mutableStateOf(0) }
    val targets =
        listOf(Target.ALL, Target.GATE, Target.JEE, Target.NEET, Target.UPSC)
    val filteredList: List<User> =
        if (selectedIndex == 0) users else users.filter { it.target == targets[selectedIndex] }

    var user by remember { mutableStateOf(User()) }
    var isDialogVisible by remember { mutableStateOf(false) }

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
                RadioButtons(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp, bottom = 8.dp),
                    options = targets.map { it.toString() },
                    selected = selectedIndex,
                    onSelect = { selectedIndex = it }
                )
            }
            items(filteredList) { u ->
                if (u.uid != currentUserId)
                    ListItem(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .height(52.dp)
                            .background(Color.Transparent),
                        leadingIcon = {
                            Image(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .clickable {
                                        user = u
                                        isDialogVisible=true
                                    },
                                painter = rememberImagePainter(data = u.photoUrl),
                                contentDescription = "user icon"
                            )
                        },
                        trailingIcon = {
                            val rating = if (u.ratings.isNotEmpty()) {
                                getAverageRating(u.ratings).toString()
                            } else {
                                "Not Rated"
                            }
                            Text(text = rating)
                        },
                        primaryText = u.name,
                        secondaryText = u.target.toString(),
                        onClick = {
                            navController.navigate("chat_screen/${u.uid}")
                        }
                    )
            }
        }
        if (isDialogVisible) {
            ProfileDialog(
                user = user,
                confirmText = "Connect",
                onDismissRequest = { isDialogVisible = false }) {
                navController.navigate("chat_screen/${user.uid}")
            }
        }

    }
}