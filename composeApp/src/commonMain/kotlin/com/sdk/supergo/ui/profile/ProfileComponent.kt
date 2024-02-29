package com.sdk.supergo.ui.profile

import com.arkivanov.decompose.ComponentContext

class ProfileComponent(
    componentContext: ComponentContext,
   // storeFactory: StoreFactory,
    private val output: (Output) -> Unit
): ComponentContext by componentContext {
//    private val mainStore = instanceKeeper.getStore {
//        IntroStoreFactory(
//            storeFactory = storeFactory
//        ).create()
//    }
//    val state: StateFlow<IntroStore.S>
//    fun onEvent(event: IntroStore.Intent) {
//        mainStore.accept(event)
//    }
    fun onOutput(output: Output) {
        output(output)
    }
    sealed interface Output {
        data object OnBack: Output
    }
}