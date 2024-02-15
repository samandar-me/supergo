package com.sdk.supergo.ui.intro

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import kotlinx.coroutines.flow.StateFlow

class IntroComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val output: (Output) -> Unit
): ComponentContext by componentContext {
    private val mainStore = instanceKeeper.getStore {
        IntroStoreFactory(
            storeFactory = storeFactory
        ).create()
    }
//    val state: StateFlow<IntroStore.S>
    fun onEvent(event: IntroStore.Intent) {
        mainStore.accept(event)
    }
    fun onOutput(output: Output) {
        output(output)
    }
    sealed interface Output {
        data object OnHumanClicked: Output
        data object OnDeliverClicked: Output
    }
}