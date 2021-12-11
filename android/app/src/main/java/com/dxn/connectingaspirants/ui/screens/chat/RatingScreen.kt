package com.dxn.connectingaspirants.ui.screens.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dxn.connectingaspirants.data.models.Target
import com.dxn.connectingaspirants.data.models.TargetParameters
import com.dxn.connectingaspirants.ui.components.RadioButtons

@Composable
fun RatingScreen(
    targetParameters: TargetParameters?,
    selected: Array<Int>,
    onSelect: (index: Int, value: Int) -> Unit
) {

    LazyColumn(Modifier.padding(0.dp)) {
        if (targetParameters != null) {
            itemsIndexed(targetParameters.parameters) { index, parameter ->
                Text(text = parameter)
                RadioButtons(
                    modifier = Modifier.fillMaxWidth(),
                    options = listOf(1, 2, 3, 4, 5),
                    selected = selected[index],
                    onSelect = { value -> onSelect(index, value) }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Preview
@Composable
fun Preview() {
    val targetParameter = TargetParameters.getTargetParams(Target.JEE)!!
    val selected = remember { targetParameter.parameters.map { 0 }.toMutableStateList() }
    RatingScreen(targetParameters = targetParameter,
        selected = selected.toTypedArray(),
        onSelect = { index, value ->
            selected[index] = value
        })
}