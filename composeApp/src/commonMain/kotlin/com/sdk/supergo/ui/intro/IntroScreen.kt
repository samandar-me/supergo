package com.sdk.supergo.ui.intro

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sdk.supergo.ui.component.AppButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IntroScreen(component: IntroComponent) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Super Go", fontSize = 20.sp)
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AppButton(
                onClick = {
                    component.onOutput(IntroComponent.Output.OnHumanClicked)
                },
                text = "Kettik!",
                size = 17
            )
            Spacer(Modifier.height(20.dp))
            AppButton(
                onClick = {
                    component.onOutput(IntroComponent.Output.OnDeliverClicked)
                },
                text = "Yetkazib berish",
                size = 17
            )
        }
    }
}