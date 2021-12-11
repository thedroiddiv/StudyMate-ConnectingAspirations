package com.dxn.connectingaspirants.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.dxn.connectingaspirants.data.models.TargetParameters
import com.dxn.connectingaspirants.data.models.User
import com.dxn.connectingaspirants.ui.screens.chat.RatingScreen
import com.dxn.connectingaspirants.ui.screens.profile.Profile


@Composable
fun ProfileDialog(
    user:User,
    dismissText : String = "Dismiss",
    confirmText : String = "Confirm",
    onDismissRequest : () -> Unit,
    onConfirmRequest : () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(Modifier.padding(16.dp)) {
                Profile(user = user)
                Spacer(modifier = Modifier.height(16.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismissRequest) {
                        Text(text = dismissText)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    TextButton(onClick = onConfirmRequest) {
                        Text(text = confirmText)
                    }
                }
            }
        }
    }

}