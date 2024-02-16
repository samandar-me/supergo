package com.sdk.supergo.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.sdk.supergo.ui.theme.SeedColor
import org.jetbrains.compose.resources.painterResource

@Composable
fun ProfileImage(
    image: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .clip(RoundedCornerShape(50))
            .border(1.dp, SeedColor, RoundedCornerShape(50))
            .clickable {
                onClick()
            }
    ) {
        Image(
            painter = painterResource("img.png"),
            contentDescription = "profile_image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}