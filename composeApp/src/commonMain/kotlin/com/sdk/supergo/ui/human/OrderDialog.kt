package com.sdk.supergo.ui.human

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CarRental
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.ui.graphics.vector.ImageVector
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
    if(state.isOrderVisible) {
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
                            imageVector = Icons.Default.Close,
                            contentDescription = "close",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            },
            text = {
                Column {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        ThreeText(
                            title = state.title1,
                            desc = state.selectedCity1,
                            icon = Icons.Default.Place
                        )
                        ThreeText(
                            title = "Odam soni",
                            desc = state.peopleCount,
                            icon = Icons.Default.Group
                        )
                    }
                    Spacer(Modifier.height(22.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        ThreeText(
                            title = state.title2,
                            desc = state.selectedCity2,
                            icon = Icons.Default.Place
                        )
                        ThreeText(
                            title = "Mashina turi",
                            desc = "fdsafsd",
                            icon = Icons.Default.CarRental
                        )
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
                            Icon(
                                    painter = painterResource("img/phone.png"),
                                contentDescription = "phone",
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
    icon: ImageVector
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null
        )
        Spacer(Modifier.width(6.dp))
        Column {
            Text(text = title, color = Color.Gray)
            Spacer(Modifier.height(6.dp))
            Text(text = desc, fontSize = 18.sp, color = Color.Black, fontWeight = FontWeight.Bold)
        }
    }
}