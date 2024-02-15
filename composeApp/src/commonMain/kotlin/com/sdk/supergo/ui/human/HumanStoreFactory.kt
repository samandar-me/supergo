package com.sdk.supergo.ui.human

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.sdk.supergo.appDispatchers

internal class HumanStoreFactory(
    private val storeFactory: StoreFactory
) {
    fun create(): HumanStore = object : HumanStore, Store<HumanStore.Intent, HumanStore.State, Nothing> by storeFactory.create(
        name = "HumanStore",
        initialState = HumanStore.State(),
        bootstrapper = SimpleBootstrapper(Unit),
        executorFactory = ::ExecutorImpl,
        reducer = ReducerImpl
    ) {}

    private sealed interface Message {
        data object Loading: Message
    }
    private inner class ExecutorImpl: CoroutineExecutor<HumanStore.Intent, Unit, HumanStore.State, Message, Nothing>(
        appDispatchers.main
    ) {
        override fun executeAction(action: Unit, getState: () -> HumanStore.State) {
            super.executeAction(action, getState)
        }

        override fun executeIntent(intent: HumanStore.Intent, getState: () -> HumanStore.State) {
            super.executeIntent(intent, getState)
        }
    }
    private object ReducerImpl: Reducer<HumanStore.State, Message> {
        override fun HumanStore.State.reduce(msg: Message): HumanStore.State {
            return copy()
        }
    }
}