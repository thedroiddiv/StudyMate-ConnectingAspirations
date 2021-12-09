package com.dxn.connectingaspirants.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dxn.connectingaspirants.R
import com.dxn.connectingaspirants.ui.components.*
import com.facebook.CallbackManager

@Composable
fun AuthScreen(
    viewModel: AuthViewModel,
    signInWithGoogle: () -> Unit,
    signInWithFacebook: () -> Unit,
    signInWithEmail: (email: String, password: String) -> Unit,
) {


    var selectedTagIndex by remember { viewModel.selectedTagIndex }
    var selectedLevelIndex by remember { viewModel.selectedLevelIndex }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Bottom
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_study),
            contentDescription = "",
            alpha = 0.8f,
            contentScale = ContentScale.FillWidth
        )
        Column {
            Column {
                HeadingText(text = stringResource(R.string.app_name))
                BodyText(text = stringResource(R.string.notes_description))
            }
            Spacer(modifier = Modifier.height(32.dp))
            RadioButtons(
                modifier = Modifier,
                options = viewModel.tags.map { it.toString() },
                selected = selectedTagIndex,
                onSelect = { selectedTagIndex = it })
            Spacer(modifier = Modifier.height(8.dp))
            RadioButtons(
                modifier = Modifier,
                options = listOf("Newbie", "Intermediate", "Advanced"),
                selected = selectedLevelIndex,
                onSelect = { selectedLevelIndex = it })
            Spacer(modifier = Modifier.height(8.dp))

        }
        RoundedButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Continue with Google"
        ) { signInWithGoogle() }
        Spacer(modifier = Modifier.height(16.dp))
        RoundedButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Continue with Facebook"
        ) { signInWithFacebook() }
        Spacer(modifier = Modifier.height(16.dp))
        HeadingText(
            modifier = Modifier.fillMaxWidth(),
            text = "OR",
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        RoundedButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Continue with Email and Password"
        ) { }
    }
}