package com.sdk.supergo.ui.intro

import com.arkivanov.mvikotlin.core.store.Store

interface IntroStore : Store<IntroStore.Intent, IntroStore.State, Nothing> {
    sealed interface Intent {

    }
    data class State(
        val empty: String = ""
    )
}