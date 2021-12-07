package com.dxn.connectingaspirants.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.dxn.connectingaspirants.ui.theme.ConnectingAspirantsTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConnectingAspirantsTheme {

            }
        }
    }


}
