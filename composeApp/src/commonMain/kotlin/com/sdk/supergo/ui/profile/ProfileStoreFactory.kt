package com.sdk.supergo.ui.profile

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor

internal class ProfileStoreFactory(
    private val storeFactory: StoreFactory
) {
    fun create(): ProfileStore = object : ProfileStore, Store<ProfileStore.Intent, ProfileStore.State, Nothing> by storeFactory.create(
        name = "ProfileStore",
        initialState = ProfileStore.State(),
        bootstrapper = SimpleBootstrapper(Unit),
        executorFactory = ::ExecutorImpl,
        reducer = ReducerImpl
    ) {}

    private inner class ExecutorImpl : CoroutineExecutor<ProfileStore.Intent, Unit, ProfileStore.State, Nothing, Nothing>()
    private object ReducerImpl: Reducer<ProfileStore.State, ProfileStore.State> {
        override fun ProfileStore.State.reduce(msg: ProfileStore.State): ProfileStore.State {
            return ProfileStore.State()
        }
    }
}