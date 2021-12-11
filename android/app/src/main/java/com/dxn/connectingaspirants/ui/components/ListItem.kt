package com.dxn.connectingaspirants.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@ExperimentalMaterialApi
@Composable
fun ListItem(
    modifier: Modifier = Modifier,
    leadingIcon: @Composable () -> Unit = {},
    primaryText: String = "",
    secondaryText: String = "",
    trailingIcon: @Composable () -> Unit = {},
    backgroundColor: Color = MaterialTheme.colors.background.copy(0.4f),
    contentColor: Color = MaterialTheme.colors.onBackground,
    cornerRadius: Dp = 16.dp,
    onClick: () -> Unit = {}
) {
    Surface(
        modifier = modifier,
        color = backgroundColor,
        contentColor = contentColor,
        shape = RoundedCornerShape(cornerRadius),
        onClick = onClick
    ) {
        Row(Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxHeight()
                    .aspectRatio(1f),
                contentAlignment = Center
            ) { leadingIcon() }
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            ) {
                Row(
                    Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    verticalAlignment = CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(Modifier.weight(1f)) {
                        TitleText(
                            text = primaryText,
                            maxLines = 1,
                        )
                        BodyText(
                            text = secondaryText,
                            maxLines = 1,
                        )
                    }
                    trailingIcon()
                }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(0.5.dp)
                        .background(MaterialTheme.colors.primary.copy(0.8f))
                        .align(BottomCenter)
                ) {}
            }
        }
    }
}