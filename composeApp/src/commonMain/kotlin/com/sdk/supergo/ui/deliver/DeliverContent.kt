package com.sdk.supergo.ui.deliver

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sdk.supergo.ui.component.CarItem
import com.sdk.supergo.ui.component.CityDropDown
import com.sdk.supergo.ui.component.Dot
import com.sdk.supergo.ui.component.Loading
import com.sdk.supergo.ui.component.NoteToDriver
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DeliverContent(
    paddingValues: PaddingValues,
    focusRequester: FocusRequester,
    state: DeliverStore.State,
    onEvent: (DeliverStore.Intent) -> Unit
) {
    val softwareKeyboardController = LocalSoftwareKeyboardController.current

    if (state.isLoading) {
        Loading()
    } else {
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .pointerInput(Unit) {
                    detectTapGestures {
                        softwareKeyboardController?.hide()
                    }
                },
            contentPadding = PaddingValues(18.dp),
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
                                onEvent(DeliverStore.Intent.OnFromChanged)
                            },
                            onSelected = { s ->
                                onEvent(DeliverStore.Intent.OnFromSelected(s))
                            },
                            cityList = state.cityList1
                        )
                        Divider(modifier = Modifier.padding(vertical = 8.dp))
                        CityDropDown(
                            title = state.title2,
                            expanded = state.toExpanded,
                            selectedCity = state.selectedCity2,
                            onChanged = {
                                onEvent(DeliverStore.Intent.OnToChanged)
                            },
                            onSelected = { s ->
                                onEvent(DeliverStore.Intent.OnToSelected(s))
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
                                    onEvent(DeliverStore.Intent.OnReplaced)
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
                NoteToDriver(
                    value = state.noteToDriver,
                    onChanged = {
                        onEvent(DeliverStore.Intent.OnNoteChanged(it))
                    }
                )
            }
        }
    }
}
