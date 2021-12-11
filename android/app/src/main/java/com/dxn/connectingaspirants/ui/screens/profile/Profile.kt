package com.dxn.connectingaspirants.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.dxn.connectingaspirants.data.models.Rating
import com.dxn.connectingaspirants.data.models.User
import com.dxn.connectingaspirants.data.models.getAverageRating
import com.dxn.connectingaspirants.ui.components.BodyText
import com.dxn.connectingaspirants.ui.components.HeadingText
import com.dxn.connectingaspirants.ui.components.TitleText

@Composable
fun Profile(
    user: User
) {
    Column(
        Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            modifier = Modifier.clip(CircleShape).size(96.dp),
            painter = rememberImagePainter(data = user.photoUrl),
            contentDescription = "user",
        )
        Spacer(modifier = Modifier.height(16.dp))
        TitleText(text = user.name)
        Spacer(modifier = Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                BodyText(text = "Rating")
                val rating = getAverageRating(user.ratings)
                HeadingText(text = if (rating == 0f) "Not Rated" else rating.toString())
            }
            Spacer(modifier = Modifier.width(32.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                BodyText(text = "Target")
                HeadingText(text = user.target.toString())
            }
            Spacer(modifier = Modifier.width(32.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                BodyText(text = "Level")
                HeadingText(text = user.level.toString())
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            textAlign = TextAlign.Center,
            text = "I am ambitious and driven. I thrive on challenge and constantly set goals for myself, so I have something to strive towards. "
        )
    }
}

@Preview()
@Composable
fun Preview() {
    val user = User(
        photoUrl = "https://picsum.photos/200",
        ratings = listOf(Rating(4f), Rating(4.223f), Rating(4.2588f))
    )
    Card(
        backgroundColor = Color.Cyan.copy(0.02f)
    ) {
        Profile(user = user)
    }
}