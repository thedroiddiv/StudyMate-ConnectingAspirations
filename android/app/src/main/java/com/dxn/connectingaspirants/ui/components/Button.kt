package com.dxn.connectingaspirants.ui.components

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.dxn.connectingaspirants.R
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import java.time.format.TextStyle

@Composable
fun RoundedButton(
    modifier: Modifier = Modifier,
    text: String,
    textPadding: Dp = 8.dp,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        shape = CircleShape,
    ) {
        Text(
            modifier = Modifier.padding(vertical = textPadding),
            text = text,
            style = MaterialTheme.typography.button
        )
    }
}

@Composable
fun FacebookLoginButton(
    modifier: Modifier = Modifier,
    onLogin: (LoginResult?) -> Unit,
    onCancel: () -> Unit,
    onFailure: (FacebookException?) -> Unit,
    callbackManager: CallbackManager,
) {
    val loginText = stringResource(R.string.txt_connect_with_facebook)
    AndroidView(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        factory = ::LoginButton,
        update = { button ->
            button.setLoginText(loginText)
            LoginManager.getInstance().registerCallback(callbackManager,
                object : FacebookCallback<LoginResult?> {
                    override fun onSuccess(result: LoginResult?) {
                        onLogin(result)
                    }

                    override fun onCancel() {
                        onCancel()
                    }

                    override fun onError(error: FacebookException) {
                        onFailure(error)
                    }
                })

        })
}


