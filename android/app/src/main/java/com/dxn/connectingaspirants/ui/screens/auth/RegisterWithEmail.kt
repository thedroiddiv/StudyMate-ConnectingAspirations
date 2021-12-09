package com.dxn.connectingaspirants.ui.screens.auth

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dxn.connectingaspirants.ui.components.TransparentTextField

@Composable
fun RegisterWithEmail(viewModel: AuthViewModel) {
    var email by remember { viewModel.email }
    var password by remember { viewModel.password }
    Column {
        TransparentTextField(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, MaterialTheme.colors.primary, CircleShape)
                .padding(16.dp),
            value = email,
            onValueChange = { email = it },
            onFocusChange = {},
            hint = "Email"
        )
        Spacer(modifier = Modifier.height(8.dp))
        TransparentTextField(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, MaterialTheme.colors.primary, CircleShape)
                .padding(16.dp),
            value = password,
            onValueChange = { password = it },
            onFocusChange = {},
            hint = "Password"
        )
        Spacer(modifier = Modifier.height(8.dp))


    }
}