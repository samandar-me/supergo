package com.sdk.supergo.ui.profile

import com.arkivanov.mvikotlin.core.store.Store

interface ProfileStore : Store<ProfileStore.Intent, ProfileStore.State, Nothing> {
    sealed interface Intent {

    }
    data class State(
        val empty: String = ""
    )
}