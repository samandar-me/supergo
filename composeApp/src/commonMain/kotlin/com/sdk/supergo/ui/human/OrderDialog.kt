package com.sdk.supergo.ui.human

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CarRental
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ElectricCar
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sdk.supergo.ui.component.AppButton
import org.jetbrains.compose.resources.painterResource

@Composable
fun OrderDialog(
    component: HumanComponent
) {
    val state by component.state.collectAsState()
    if (state.isOrderVisible) {
        AlertDialog(
            onDismissRequest = {
                component.onEvent(HumanStore.Intent.OnCloseOrder)
            },
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Sizning buyurtmangiz", fontSize = 20.sp)
                    IconButton(
                        onClick = {
                            component.onEvent(HumanStore.Intent.OnCloseOrder)
                        }
                    ) {
                        Icon(
                            painter = painterResource("img/close.png"),
                            contentDescription = "close",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            },
            text = {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            ThreeText(
                                title = state.title1,
                                desc = state.selectedCity1.name,
                                icon = "pushpin.png"
                            )
                            Spacer(Modifier.height(26.dp))
                            ThreeText(
                                title = state.title2,
                                desc = state.selectedCity2.name,
                                icon = "location.png"
                            )
                        }
                        Column {
                            Spacer(
                                modifier = Modifier
                                    .height(120.dp)
                                    .width(1.dp)
                                    .background(Color.DarkGray)
                            )
                        }
                        Column {
                            ThreeText(
                                title = "Odam soni",
                                desc = state.peopleCount,
                                icon = "group.png"
                            )
                            Spacer(Modifier.height(26.dp))
                            ThreeText(
                                title = "Mashina turi",
                                desc = state.carList[state.selectedCarIndex],
                                icon = "car_icon.png"
                            )
                        }
                    }
                    Spacer(Modifier.height(30.dp))
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        value = state.number,
                        onValueChange = {
                            component.onEvent(HumanStore.Intent.OnNumberChanged(it))
                        },
                        placeholder = {
                            Text(text = "Telefon raqam")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        leadingIcon = {
                            Row {
                                Icon(
                                    painter = painterResource("img/phone.png"),
                                    contentDescription = "phone",
                                    modifier = Modifier.size(22.dp),
                                    tint = Color.Black
                                )
                                Spacer(Modifier.width(8.dp))
                                Text(text = "+998", color = Color.Black, fontSize = 18.sp)
                            }
                        },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Gray
                        ),
                        textStyle = TextStyle(
                            fontSize = 18.sp
                        )
                    )
                }
            },
            confirmButton = {
                AppButton(
                    onClick = {
                        component.onEvent(HumanStore.Intent.OnSendCode)
                    },
                    text = "Kod jo’natish"
                )
            }
        )
    }
}

@Composable
private fun ThreeText(
    title: String,
    desc: String,
    icon: String
) {
    Row {
        Image(
            painter = painterResource("img/$icon"),
            contentDescription = "icon",
            colorFilter = ColorFilter.tint(color = Color.Black),
            modifier = Modifier.size(20.dp)
        )
        Spacer(Modifier.width(6.dp))
        Column {
            Text(text = title, color = Color.Gray)
            Spacer(Modifier.height(6.dp))
            Text(text = desc, fontSize = 18.sp, color = Color.Black, fontWeight = FontWeight.Bold)
        }
    }
}