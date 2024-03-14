package com.sdk.supergo.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sdk.supergo.data.model.Car
import com.sdk.supergo.ui.theme.SeedColor
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.model.ImageAction
import com.seiko.imageloader.model.ImageResult
import com.seiko.imageloader.rememberImageAction
import com.seiko.imageloader.rememberImageActionPainter
import com.seiko.imageloader.rememberImagePainter
import com.seiko.imageloader.rememberImageResultPainter
import compose.icons.FeatherIcons
import org.jetbrains.compose.resources.painterResource

@Composable
fun CarItem(
    car: Car,
    selected: Boolean,
    onSelected: () -> Unit
) {
    val action = rememberImageAction(car.icon)

    Box(
        modifier = Modifier
            .height(150.dp)
            .width(200.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier.fillMaxWidth().weight(1f)
            ) {}
            Surface(
                modifier = Modifier.fillMaxWidth().weight(3f),
                onClick = onSelected,
                color = Color.White,
                shadowElevation = 2.dp,
                shape = RoundedCornerShape(18.dp)
            ) {
                Column {
                    Surface(
                        modifier = Modifier.fillMaxWidth(.7f).weight(2f),
                        shape = RoundedCornerShape(topStart = 18.dp, bottomEnd = 18.dp),
                        color = if (selected) SeedColor else Color.LightGray
                    ) {
                        if (action.value is ImageAction.Loading) {
                            Box(modifier = Modifier.size(24.dp), contentAlignment = Alignment.Center) {
                                Loading()
                            }
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth().weight(1.2f).padding(horizontal = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = car.name, fontWeight = FontWeight.Bold)
                        Text(text = "${car.price ?: 0}", fontWeight = FontWeight.Bold)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth().weight(1.2f).padding(horizontal = 20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = car.count.toString(), fontSize = 12.sp, color = Color.Gray)
                        Spacer(Modifier.width(12.dp))
                        Icon(
                            Icons.Default.Person,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp),
                            tint = Color.LightGray
                        )
                        Text(text = "4", fontSize = 12.sp, color = Color.Gray)
                    }
                }
            }
        }
        if (action.value is ImageAction.Success) {
            Image(
                painter = rememberImageActionPainter(action.value),
                contentDescription = null,
                modifier = Modifier.size(140.dp),
                alignment = Alignment.TopStart
            )
        }
    }
}