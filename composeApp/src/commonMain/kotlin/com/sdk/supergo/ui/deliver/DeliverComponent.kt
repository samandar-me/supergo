package com.sdk.supergo.ui.deliver

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow

class DeliverComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    private val output: (Output) -> Unit
): ComponentContext by componentContext {
    private val humanStore = instanceKeeper.getStore {
        HumanStoreFactory(
            storeFactory = storeFactory
        ).create()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<DeliverStore.State> = humanStore.stateFlow

    fun onEvent(event: DeliverStore.Intent) {
        humanStore.accept(event)
    }
    fun onOutput(output: Output) {
        output(output)
    }
    sealed interface Output {
        data object OnBack: Output
        data object OnSuccess: Output
    }
}