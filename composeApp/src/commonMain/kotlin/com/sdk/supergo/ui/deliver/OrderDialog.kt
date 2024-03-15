package com.sdk.supergo.ui.deliver

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sdk.supergo.core.MaskVisualTransformation
import com.sdk.supergo.ui.component.AppButton
import com.sdk.supergo.ui.component.Loading
import org.jetbrains.compose.resources.painterResource

@Composable
fun OrderDialog(
    state: DeliverStore.State,
    onEvent: (DeliverStore.Intent) -> Unit
) {
    if (state.isOrderVisible) {
        AlertDialog(
            modifier = Modifier.windowInsetsPadding(WindowInsets.ime),
            onDismissRequest = {
                onEvent(DeliverStore.Intent.OnCloseOrder)
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
                            onEvent(DeliverStore.Intent.OnCloseOrder)
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
                    ThreeText(
                        title = state.title1,
                        desc = state.selectedCity1.name,
                        icon = "pushpin.png"
                    )
                    Divider(modifier = Modifier.fillMaxWidth().padding(12.dp))
                    ThreeText(
                        title = state.title2,
                        desc = state.selectedCity2.name,
                        icon = "location.png"
                    )
                    Spacer(Modifier.height(10.dp))
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        value = state.number,
                        onValueChange = {
                            if (it.length < 10) {
                                onEvent(DeliverStore.Intent.OnNumberChanged(it))
                            }
                        },
                        placeholder = {
                            Text(text = "Telefon raqam")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
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
                        ),
                        visualTransformation = MaskVisualTransformation("(##) ### ## ##")
                    )
                }
            },
            confirmButton = {
                if (state.isOrderBtnLoading) {
                    Row(modifier = Modifier.fillMaxWidth().height(55.dp)) {
                        Loading()
                    }
                } else {
                    AppButton(
                        onClick = {
                            if (state.number.trim().length != 9) {
                                return@AppButton
                            }
                            onEvent(DeliverStore.Intent.OnSendCode)
                        },
                        text = "Kod jo’natish"
                    )
                }
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
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
            Text(
                text = desc,
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                modifier = Modifier.basicMarquee(
                    iterations = Int.MAX_VALUE
                )
            )
        }
    }
}