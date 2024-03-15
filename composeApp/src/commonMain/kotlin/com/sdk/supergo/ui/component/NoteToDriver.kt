package com.sdk.supergo.ui.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.painterResource

@Composable
fun NoteToDriver(
    value: String,
    onChanged: (String) -> Unit
) {
    Spacer(Modifier.height(20.dp))
    Text(
        text = "Haydovchiga eslatma",
        fontSize = 18.sp,
        color = Color.Black,
        fontWeight = FontWeight.Bold
    )
    Spacer(Modifier.height(12.dp))
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        value = value,
        onValueChange = onChanged,
        placeholder = {
            Text(text = "Bu yerga yozing")
        },
        leadingIcon = {
            Icon(
                painter = painterResource("img/comment.png"),
                contentDescription = "comment",
                modifier = Modifier.size(20.dp),
                tint = Color.Black
            )
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Gray
        )
    )
    Spacer(Modifier.height(16.dp))
}
