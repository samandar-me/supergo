package com.sdk.supergo.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sdk.supergo.ui.theme.SeedColor

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    size: Int = 16
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(55.dp)
            .clip(RoundedCornerShape(12.dp)),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = SeedColor,
            contentColor = Color.Black
        )
    ) {
        Text(text = text, fontSize = (size).sp)
    }
}