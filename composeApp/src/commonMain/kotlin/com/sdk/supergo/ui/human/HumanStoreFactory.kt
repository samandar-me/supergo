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
        data object OnFromChanged: Message
        data object OnToChanged: Message
        data class OnFromSelected(val city: String): Message
        data class OnToSelected(val city: String): Message
        data object OnReplaced:  Message
    }
    private inner class ExecutorImpl: CoroutineExecutor<HumanStore.Intent, Unit, HumanStore.State, Message, Nothing>(
        appDispatchers.main
    ) {
        override fun executeAction(action: Unit, getState: () -> HumanStore.State) {
            super.executeAction(action, getState)
        }

        override fun executeIntent(intent: HumanStore.Intent, getState: () -> HumanStore.State) {
            when(intent) {
                is HumanStore.Intent.OnFromChanged -> dispatch(Message.OnFromChanged)
                is HumanStore.Intent.OnToChanged -> dispatch(Message.OnToChanged)
                is HumanStore.Intent.OnFromSelected -> dispatch(Message.OnFromSelected(intent.city))
                is HumanStore.Intent.OnToSelected -> dispatch(Message.OnToSelected(intent.city))
                is HumanStore.Intent.OnReplaced -> dispatch(Message.OnReplaced)
            }
        }
    }
    private object ReducerImpl: Reducer<HumanStore.State, Message> {
        override fun HumanStore.State.reduce(msg: Message): HumanStore.State {
            return when(msg) {
                is Message.Loading -> copy()
                is Message.OnFromChanged -> copy(fromExpanded = !fromExpanded)
                is Message.OnToChanged -> copy(toExpanded = !toExpanded)
                is Message.OnReplaced -> {
                    copy()
                }
                is Message.OnFromSelected-> copy(selectedCity1 = msg.city, fromExpanded = false)
                is Message.OnToSelected-> copy(selectedCity2 = msg.city, toExpanded = false)
            }
        }
    }
}