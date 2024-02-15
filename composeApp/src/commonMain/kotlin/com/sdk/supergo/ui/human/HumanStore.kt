package com.sdk.supergo.ui.human

import com.arkivanov.mvikotlin.core.store.Store

interface HumanStore : Store<HumanStore.Intent, HumanStore.State, Nothing> {
    sealed interface Intent {

    }
    data class State(
        val empty: String = ""
    )
}