package com.dxn.connectingaspirants.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.dxn.connectingaspirants.ui.components.TitleText
import com.dxn.connectingaspirants.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun Home(
    viewModel: HomeViewModel,
    currentUser: FirebaseUser,
    navController: NavController
) {

    val pagerState = rememberPagerState()
    val pages = listOf("Feeds", "Explore")
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TitleText(text = "Hello!\n${currentUser.displayName!!}", maxLines = 2)
                Row() {
                    IconButton(
                        modifier = Modifier.size(40.dp),
                        onClick = {}) {
                        Image(
                            modifier = Modifier.clip(CircleShape),
                            painter = rememberImagePainter(data = currentUser.photoUrl),
                            contentDescription = "user profile",
                        )
                    }
                    IconButton(
                        modifier = Modifier.size(40.dp),
                        onClick = {navController.navigate("chats_screen")}) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_chat),
                            contentDescription = "chats",
                            tint = MaterialTheme.colors.primary,
                        )
                    }
                }
            }
        },
        bottomBar = {
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                    )
                }
            ) {
                pages.forEachIndexed { index, s ->
                    Tab(
                        text = { Text(s) },
                        selected = pagerState.currentPage == 0,
                        onClick = {
                            scope.launch {
                                pagerState.scrollToPage(index)
                            }
                        },
                    )
                }
            }
        }
    ) {
        HorizontalPager(count = 2, state = pagerState) { page ->
            when (page) {
                0 -> {
                    Feeds()
                }
                1 -> {
                    Explore()
                }
            }
        }
    }
}