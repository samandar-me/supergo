package com.sdk.supergo.ui.human

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sdk.supergo.ui.component.CityDropDown
import com.sdk.supergo.ui.component.Dot
import com.sdk.supergo.ui.component.ProfileImage
import compose.icons.AllIcons
import compose.icons.FeatherIcons
import compose.icons.feathericons.ArrowLeft
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HumanScreen(component: HumanComponent) {
    val state by component.state.collectAsState()
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
                            .weight(.8f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text("\uD83D\uDCCD", fontSize = 22.sp)
                        Column {
                            for (i in 0 until 16) {
                                Spacer(Modifier.padding(1.dp))
                                Dot()
                                Spacer(Modifier.padding(1.dp))
                            }
                        }
                        Text(text = "➤", fontSize = 22.sp)
                        Spacer(Modifier.height(7.dp))
                    }
                    Spacer(Modifier.width(16.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(4f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        CityDropDown(
                            title = "Andijan",
                            expanded = state.fromExpanded,
                            selectedCity = state.selectedCity1,
                            onChanged = {
                                component.onEvent(HumanStore.Intent.OnFromChanged)
                            },
                            onSelected = { s ->
                                component.onEvent(HumanStore.Intent.OnFromSelected(s))
                            },
                            cityList = state.cityList1
                        )
                        Divider(modifier = Modifier.padding(vertical = 8.dp))
                        CityDropDown(
                            title = "Tashkent",
                            expanded = state.toExpanded,
                            selectedCity = state.selectedCity2,
                            onChanged = {
                                component.onEvent(HumanStore.Intent.OnToChanged)
                            },
                            onSelected = { s ->
                                component.onEvent(HumanStore.Intent.OnToSelected(s))
                            },
                            cityList = state.cityList2
                        )
                    }
                    Spacer(Modifier.width(16.dp))
                    Box(
                        modifier = Modifier.fillMaxHeight().weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier.size(45.dp).clip(CircleShape)
                                .background(Color(0xFFEDEDED)).clickable {
                                    component.onEvent(HumanStore.Intent.OnReplaced)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource("img/replace.png"),
                                contentDescription = "replace",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}