package com.dxn.connectingaspirants.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun EditMessageField(
    value: String,
    onChange: (String) -> Unit,
    onSubmit: () -> Unit
) {
    Row(modifier = Modifier
        .padding(8.dp)
        .border(1.dp, Color.Black, CircleShape)
        .background(MaterialTheme.colors.background)
        .padding(horizontal = 16.dp,vertical = 0.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TransparentTextField(
            modifier = Modifier.weight(1f),
            value = value,
            hint = "Message",
            textStyle = MaterialTheme.typography.body1,
            onValueChange = onChange,
            onFocusChange = {

            }
        )
        IconButton(onClick = { onSubmit() }) {
            Icon(imageVector = Icons.Rounded.Send, contentDescription = "send button")
        }
    }
}