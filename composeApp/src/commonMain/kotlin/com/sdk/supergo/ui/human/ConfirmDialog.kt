package com.sdk.supergo.ui.human

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CarRental
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ElectricCar
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sdk.supergo.ui.component.AppButton
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource

@Composable
fun ConfirmDialog(
    component: HumanComponent
) {
    val state by component.state.collectAsState()
    if (state.isConfirmVisible) {
        var downTime by remember { mutableStateOf(60) }
        LaunchedEffect(Unit) {
            while (downTime > 0) {
                delay(1000L)
                downTime--
            }
        }
        AlertDialog(
            onDismissRequest = {
                component.onEvent(HumanStore.Intent.OnCloseConfirm)
            },
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = {
                            component.onEvent(HumanStore.Intent.OnBackToOrder)
                        }
                    ) {
                        Icon(Icons.Default.ArrowBack, "back")
                    }
                    Text(text = "Kod orqali tasdiqlash", fontSize = 20.sp)
                    IconButton(
                        onClick = {
                            component.onEvent(HumanStore.Intent.OnCloseConfirm)
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
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Sizning +998${state.number} raqamingizga kod yuborildi. "
                    )
                    Spacer(Modifier.height(8.dp))
                    if (downTime == 0) {
                        TextButton(
                            onClick = {

                            }
                        ) {
                            Text(text = "Qayta jo'natish")
                        }
                    } else {
                        Text(text = downTime.toString(), fontSize = 20.sp)
                    }
                }
            },
            confirmButton = {
                AppButton(
                    onClick = {
                        component.onEvent(HumanStore.Intent.OnConfirmClicked)
                    },
                    text = "Tastiqlash"
                )
            }
        )
    }
}
