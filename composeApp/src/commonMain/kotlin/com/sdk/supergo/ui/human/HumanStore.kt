package com.sdk.supergo.ui.human

import com.arkivanov.mvikotlin.core.store.Store

interface HumanStore : Store<HumanStore.Intent, HumanStore.State, Nothing> {
    sealed interface Intent {
        data object OnFromChanged: Intent
        data object OnToChanged: Intent
        data class OnFromSelected(val city: String): Intent
        data class OnToSelected(val city: String): Intent
        data object OnReplaced: Intent
    }
    data class State(
        var fromExpanded: Boolean = false,
        var toExpanded: Boolean = false,
        val cityList1: List<String> = listOf("Asaka", "Xo'jabod", "Qo'rg'ontepa"),
        val cityList2: List<String> = listOf("Chorsu", "Qo'yliq", "Shayxontoxur"),
        var selectedCity1: String = cityList1[0],
        var selectedCity2: String = cityList2[0]
    )
}