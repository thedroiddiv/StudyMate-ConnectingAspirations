package com.dxn.connectingaspirants.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle

@Composable
fun InputField() {

}


@Composable
fun TransparentTextField(
    modifier: Modifier = Modifier,
    value: String,
    hint: String,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = TextStyle(),
    isSingleLine: Boolean = false,
    onFocusChange: (FocusState) -> Unit,
) {
    Box(modifier) {
        BasicTextField(
            value = value,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { onFocusChange(it) },
            onValueChange = onValueChange,
            singleLine = isSingleLine,
            textStyle = textStyle.copy(
                color = MaterialTheme.colors.onBackground
            ),
            cursorBrush = SolidColor(MaterialTheme.colors.onBackground)
        )
        if (value=="") {
            Text(
                text = hint,
                style = textStyle,
                color = MaterialTheme.colors.onBackground.copy(0.4f)
            )
        }
    }
}

