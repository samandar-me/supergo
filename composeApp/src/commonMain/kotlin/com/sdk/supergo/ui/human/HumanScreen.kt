package com.sdk.supergo.ui.human

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sdk.supergo.ui.component.AppButton
import com.sdk.supergo.ui.component.ProfileImage

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
        },
        bottomBar = {
            AppButton(
                modifier = Modifier.padding(16.dp),
                text = "Buyurtma berish",
                onClick = {
                    component.onEvent(HumanStore.Intent.OnShowOrder)
                }
            )
        }
    ) {
        HumanContent(component, it)
        OrderDialog(component = component)
        ConfirmDialog(component = component)
    }
}