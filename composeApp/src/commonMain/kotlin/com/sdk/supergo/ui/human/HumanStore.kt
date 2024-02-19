package com.sdk.supergo.ui.human

import com.arkivanov.mvikotlin.core.store.Store

interface HumanStore : Store<HumanStore.Intent, HumanStore.State, Nothing> {
    sealed interface Intent {
        data object OnFromChanged: Intent
        data object OnToChanged: Intent
        data class OnFromSelected(val city: String): Intent
        data class OnToSelected(val city: String): Intent
        data object OnReplaced: Intent

        data class OnPeopleCountChanged(val count: String): Intent
        data class OnCarSelected(val index: Int): Intent
    }
    data class State(
        val title1: String = "Andijan",
        val title2: String = "Tashkent",
        val fromExpanded: Boolean = false,
        val toExpanded: Boolean = false,
        val cityList1: List<String> = listOf("Asaka", "Xo'jabod", "Qo'rg'ontepa"),
        val cityList2: List<String> = listOf("Chorsu", "Qo'yliq", "Shayxontoxur"),
        val selectedCity1: String = cityList1[0],
        val selectedCity2: String = cityList2[0],
        val peopleCount: String = "",
        val selectedCarIndex: Int = -1
    )
}