package com.sdk.supergo.ui.intro

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.flow.StateFlow

class IntroComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val output: (Output) -> Unit
): ComponentContext by componentContext {
    private val introStore = instanceKeeper.getStore {
        IntroStoreFactory(
            storeFactory = storeFactory
        ).create()
    }
    val state: StateFlow<IntroStore.State> = introStore.stateFlow
    fun onEvent(event: IntroStore.Intent) {
        introStore.accept(event)
    }
    fun onOutput(output: Output) {
        output(output)
    }
    sealed interface Output {
        data object OnHumanClicked: Output
        data object OnDeliverClicked: Output
        data object OnProfileClicked: Output
    }
}