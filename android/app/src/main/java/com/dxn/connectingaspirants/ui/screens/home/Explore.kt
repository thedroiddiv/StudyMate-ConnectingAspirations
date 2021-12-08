package com.dxn.connectingaspirants.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import coil.compose.rememberImagePainter
import com.dxn.connectingaspirants.ui.components.ListItem

@ExperimentalMaterialApi
@Composable
fun Explore() {
    LazyColumn {
        items(20) {
            ListItem(
                leadingIcon = {
                    Image(
                        painter = rememberImagePainter(data = ""),
                        contentDescription = "user icon"
                    )
                },
                trailingIcon = {

                }) {

            }
        }
    }
}