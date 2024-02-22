package com.sdk.supergo.ui.human

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sdk.supergo.ui.component.AppButton
import com.sdk.supergo.ui.component.CarItem
import com.sdk.supergo.ui.component.CityDropDown
import com.sdk.supergo.ui.component.Dot
import compose.icons.FeatherIcons
import compose.icons.feathericons.MessageCircle
import org.jetbrains.compose.resources.painterResource

@Composable
fun HumanContent(component: HumanComponent, paddingValues: PaddingValues) {
    val state by component.state.collectAsState()
    LazyColumn(
        modifier = Modifier.padding(paddingValues),
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
                    Image(
                        painter = painterResource("img/pushpin.png"),
                        contentDescription = "push",
                        modifier = Modifier.size(22.dp)
                    )
                    Column {
                        for (i in 0 until 16) {
                            Spacer(Modifier.padding(1.dp))
                            Dot()
                            Spacer(Modifier.padding(1.dp))
                        }
                    }
                    Image(
                        painter = painterResource("img/location.png"),
                        contentDescription = "pin",
                        modifier = Modifier.size(22.dp)
                    )
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
                        title = state.title1,
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
                        title = state.title2,
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
        item {
            PeopleCountSection(
                state.peopleCount,
                onChanged = {
                    component.onEvent(HumanStore.Intent.OnPeopleCountChanged(it))
                }
            )
        }
        item {
            Spacer(Modifier.height(20.dp))
            Text(
                text = "Mashina tanlang",
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(12.dp))
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                itemsIndexed(
                    items = state.carList,
                    itemContent = { index, _ ->
                        CarItem(
                            selected = state.selectedCarIndex == index,
                            onSelected = {
                                component.onEvent(HumanStore.Intent.OnCarSelected(index))
                            }
                        )
                    }
                )
            }
        }
        item {
            AdditionSection(
                luggage = state.luggage,
                conditioner = state.con,
                largeLug = state.largeL,
                onConditionerChanged = {
                    component.onEvent(HumanStore.Intent.OnCon)
                },
                onLuggageChanged = {
                    component.onEvent(HumanStore.Intent.OnLuggage)
                },
                onLargeLugChanged = {
                    component.onEvent(HumanStore.Intent.OnLarge)
                },
            )
        }
        item {
            NoteToDriver(
                value = state.noteToDriver,
                onChanged = {
                    component.onEvent(HumanStore.Intent.OnNoteChanged(it))
                }
            )
        }
    }
}

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

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun AdditionSection(
    luggage: Boolean,
    conditioner: Boolean,
    largeLug: Boolean,
    onLuggageChanged: () -> Unit,
    onConditionerChanged: () -> Unit,
    onLargeLugChanged: () -> Unit,
) {
    Spacer(Modifier.height(20.dp))
    Text(
        text = "Qo’shimcha",
        fontSize = 18.sp,
        color = Color.Black,
        fontWeight = FontWeight.Bold
    )
    Spacer(Modifier.height(12.dp))
    FlowRow {
        CheckText(
            title = "Yukxona",
            checked = luggage,
            onChecked = onLuggageChanged
        )
        CheckText(
            title = "Katta xajmdagi yuk",
            checked = largeLug,
            onChecked = onLargeLugChanged
        )
        CheckText(
            title = "Kanditsaner",
            checked = conditioner,
            onChecked = onConditionerChanged
        )
    }
}

@Composable
private fun CheckText(
    title: String,
    checked: Boolean,
    onChecked: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = {
                onChecked()
            },
            colors = CheckboxDefaults.colors(
                checkmarkColor = Color.White,
                checkedColor = Color(0xFF007BFF)
            )
        )
        Text(
            text = title
        )
    }
}

@Composable
private fun PeopleCountSection(
    value: String,
    onChanged: (String) -> Unit
) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        value = value,
        onValueChange = {
            if (it.length <= 2) onChanged(it)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        leadingIcon = {
            Icon(
                painter = painterResource("img/group.png"),
                contentDescription = "group",
                modifier = Modifier.size(20.dp),
                tint = Color.Black
            )
        },
        placeholder = {
            Text(text = "Nechta odam")
        },
        singleLine = true,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Gray
        )
    )
}