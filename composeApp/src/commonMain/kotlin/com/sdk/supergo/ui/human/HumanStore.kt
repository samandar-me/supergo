package com.sdk.supergo.ui.human

import com.arkivanov.mvikotlin.core.store.Store

interface HumanStore : Store<HumanStore.Intent, HumanStore.State, Nothing> {
    sealed interface Intent {
        data object OnFromChanged: Intent
        data object OnToChanged: Intent
        data class OnFromSelected(val city: String): Intent
        data class OnToSelected(val city: String): Intent
        data object OnReplaced: Intent
        data object OnLuggage: Intent
        data object OnLarge: Intent
        data object OnCon: Intent
        data object OnOrderClicked: Intent

        data class OnPeopleCountChanged(val count: String): Intent
        data class OnCarSelected(val index: Int): Intent
        data class OnNoteChanged(val note: String): Intent
        data object OnCloseOrder: Intent
        data class OnNumberChanged(val value: String): Intent
        data object OnSendCode: Intent
        data object OnShowOrder: Intent
        data object OnCloseConfirm: Intent
        data object OnBackToOrder: Intent
        data object OnConfirmClicked: Intent
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
        val selectedCarIndex: Int = -1,
        val carList: List<String> = listOf("Gentra","Captive","Damas","Nexia","Malibue"),
        val con: Boolean = false,
        val luggage: Boolean = false,
        val largeL: Boolean = false,
        val noteToDriver: String = "",
        val number: String = "",
        val isOrderVisible: Boolean = false,
        val isConfirmVisible: Boolean = false,
        val code: String = ""
    )
}