package com.sdk.supergo.ui.root

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.scale
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.sdk.supergo.ui.deliver.DeliverScreen
import com.sdk.supergo.ui.deliver.DeliverStore
import com.sdk.supergo.ui.human.HumanScreen
import com.sdk.supergo.ui.intro.IntroScreen
import com.sdk.supergo.ui.profile.ProfileScreen

@Composable
fun RootContent(component: RootComponent) {
    MaterialTheme(
        colorScheme = lightColorScheme(background = Color.White, primary = Color.Black, surface = Color.White)
    ) {
        Children(
            stack = component.childStack
        ) {
            when(val child = it.instance) {
                is RootComponent.Child.Intro -> IntroScreen(child.component)
                is RootComponent.Child.Human -> HumanScreen(child.component)
                is RootComponent.Child.Deliver -> DeliverScreen(child.component)
                is RootComponent.Child.Profile -> ProfileScreen(child.component)
            }
        }
    }
}