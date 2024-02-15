package com.sdk.supergo.ui.intro

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sdk.supergo.ui.component.AppButton

@Composable
fun IntroScreen(component: IntroComponent) {
    Scaffold {
        Column(
            modifier = Modifier.fillMaxSize().padding(it).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AppButton(
                onClick = {
                    component.onOutput(IntroComponent.Output.OnHumanClicked)
                },
                text = "Kettik!"
            )
            Spacer(Modifier.height(20.dp))
            AppButton(
                onClick = {
                    component.onOutput(IntroComponent.Output.OnHumanClicked)
                },
                text = "Yetkazib berish"
            )
        }
    }
}