package com.dxn.connectingaspirants.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun RadioButtons(
    modifier: Modifier,
    options: List<String>,
    selected: Int,
    onSelect: (Int) -> Unit
) {
    LazyRow(modifier.fillMaxWidth()) {
        itemsIndexed(options) { index, option ->
            val isSelected = (index == selected)
            Row(
                Modifier
                    .padding(horizontal = 4.dp)
                    .clip(CircleShape)
                    .border(
                        (0.4).dp,
                        if (!isSelected) MaterialTheme.colors.primary else Color.Transparent,
                        CircleShape
                    )
                    .background(
                        if (isSelected) MaterialTheme.colors.primary.copy(0.1f) else Color.Transparent,
                        CircleShape
                    )
                    .clickable { onSelect(index) }
                    .padding(4.dp)
            ) {
                HeadingText(
                    modifier = Modifier.padding(4.dp),
                    text = option,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

