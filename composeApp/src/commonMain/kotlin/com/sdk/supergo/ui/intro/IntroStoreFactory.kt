package com.sdk.supergo.ui.intro

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor

internal class IntroStoreFactory(
    private val storeFactory: StoreFactory
) {
    fun create(): IntroStore = object : IntroStore, Store<IntroStore.Intent, IntroStore.State, Nothing> by storeFactory.create(
        name = "IntroStore",
        initialState = IntroStore.State(),
        bootstrapper = SimpleBootstrapper(Unit),
        executorFactory = ::ExecutorImpl,
        reducer = ReducerImpl
    ) {}

    private inner class ExecutorImpl : CoroutineExecutor<IntroStore.Intent, Unit, IntroStore.State, Nothing, Nothing>()
    private object ReducerImpl: Reducer<IntroStore.State, IntroStore.State> {
        override fun IntroStore.State.reduce(msg: IntroStore.State): IntroStore.State {
            return IntroStore.State()
        }
    }
}