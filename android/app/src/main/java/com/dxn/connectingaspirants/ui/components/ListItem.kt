package com.dxn.connectingaspirants.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier,
        color = backgroundColor,
        contentColor = contentColor,
        shape = RoundedCornerShape(cornerRadius),
        onClick = onClick
    ) {
        Row(
            Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
            ) {
                leadingIcon()
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                ) {
                    Column {
                        Text(
                            text = primaryText,
                            style = MaterialTheme.typography.subtitle1,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Text(
                            text = secondaryText,
                            style = MaterialTheme.typography.subtitle2,
                            maxLines = 1,
                            color = contentColor.copy(0.5f)
                        )
                    }
                }
            }
            trailingIcon()
        }
    }
}
