package com.sdk.supergo.ui.human

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Forward
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sdk.supergo.ui.component.Dot
import com.sdk.supergo.ui.component.ProfileImage
import compose.icons.AllIcons
import compose.icons.FeatherIcons
import compose.icons.feathericons.ArrowLeft

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HumanScreen(component: HumanComponent) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Qayerga?")
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            component.onOutput(HumanComponent.Output.OnBack)
                        }
                    ) {
                        Icon(Icons.Default.ArrowBackIosNew, "back")
                    }
                },
                actions = {
                    ProfileImage(
                        image = "",
                        onClick = {

                        }
                    )
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(it),
            contentPadding = PaddingValues(18.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(text = "➤", fontSize = 22.sp)
                        Column {
                            for (i in 0 until 15) {
                                Dot()
                                Spacer(Modifier.padding(2.dp))
                            }
                        }
                        Text("\uD83D\uDCCD", fontSize = 22.sp)
                    }
                    Spacer(Modifier.width(16.dp))
                    Text(text ="ffjkdlsajfdslajfdksl")
                }
            }
        }
    }
}