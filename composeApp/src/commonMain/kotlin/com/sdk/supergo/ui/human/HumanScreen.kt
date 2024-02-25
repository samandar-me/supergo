package com.sdk.supergo.ui.human

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import com.sdk.supergo.ui.component.AppButton
import com.sdk.supergo.ui.component.ProfileImage
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HumanScreen(component: HumanComponent) {
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val state by component.state.collectAsState()
    val focusRequester = remember { FocusRequester() }
    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackBarHostState
            )
        },
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
                }
            )
        },
        bottomBar = {
            AppButton(
                modifier = Modifier.padding(16.dp),
                text = "Buyurtma berish",
                onClick = {
                    if (state.peopleCount.isBlank()) {
                        focusRequester.requestFocus()
                        scope.launch {
                            snackBarHostState.currentSnackbarData?.dismiss()
                            snackBarHostState.showSnackbar("Odamlar sonini kiriting!")
                        }
                        return@AppButton
                    }
                    if (state.selectedCarIndex == -1) {
                        scope.launch {
                            snackBarHostState.currentSnackbarData?.dismiss()
                            snackBarHostState.showSnackbar("Mashina tanlang!")
                        }
                        return@AppButton
                    }
                    component.onEvent(HumanStore.Intent.OnShowOrder)
                }
            )
        }
    ) {
        HumanContent(
            paddingValues = it,
            state = state,
            focusRequester = focusRequester,
            onEvent = component::onEvent
        )
        OrderDialog(
            state = state,
            onEvent = component::onEvent
        )
        ConfirmDialog(
            state = state,
            onEvent = component::onEvent
        )
    }
}