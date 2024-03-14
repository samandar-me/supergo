package com.sdk.supergo.ui.intro

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.sdk.supergo.util.logDe
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

internal class IntroStoreFactory(
    private val storeFactory: StoreFactory
) {
    fun create(): IntroStore = object : IntroStore,
        Store<IntroStore.Intent, IntroStore.State, Nothing> by storeFactory.create(
            name = "IntroStore",
            initialState = IntroStore.State(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Msg {
        data class OnChangeOrderSuccess(val isSuccess: Boolean) : Msg
        data object OnChangeOrderToFalse : Msg
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<IntroStore.Intent, Unit, IntroStore.State, Msg, Nothing>()
    private object ReducerImpl : Reducer<IntroStore.State, Msg> {
        override fun IntroStore.State.reduce(msg: Msg): IntroStore.State {
            return copy()
        }
    }
}