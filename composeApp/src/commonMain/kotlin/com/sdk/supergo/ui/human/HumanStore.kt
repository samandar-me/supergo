package com.sdk.supergo.ui.human

import com.arkivanov.mvikotlin.core.store.Store
import com.sdk.supergo.data.model.Car
import com.sdk.supergo.data.model.CityItem
import com.sdk.supergo.data.model.FakeCityItem

interface HumanStore : Store<HumanStore.Intent, HumanStore.State, Nothing> {
    sealed interface Intent {
        data object OnFromChanged: Intent
        data object OnToChanged: Intent
        data class OnFromSelected(val city: CityItem): Intent
        data class OnToSelected(val city: CityItem): Intent
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
        data class OnOtpChanged(val value: String): Intent
        data object OnSendCode: Intent
        data object OnShowOrder: Intent
        data object OnCloseConfirm: Intent
        data object OnBackToOrder: Intent
        data object OnConfirmClicked: Intent
    }
    data class State(
        val isLoading: Boolean = true,
        val title1: String = "Andijan",
        val title2: String = "Tashkent",
        val fromExpanded: Boolean = false,
        val toExpanded: Boolean = false,
        val cityList1: List<CityItem> = emptyList(),
        val cityList2: List<CityItem> = emptyList(),
        val selectedCity1: CityItem = FakeCityItem,
        val selectedCity2: CityItem = FakeCityItem,
        val peopleCount: String = "",
        val selectedCarIndex: Int = -1,
        val carList: List<Car> = emptyList(),
        val con: Boolean = false,
        val luggage: Boolean = false,
        val largeL: Boolean = false,
        val noteToDriver: String = "",
        val number: String = "",
        val isOrderVisible: Boolean = false,
        val isConfirmVisible: Boolean = false,
        val code: String = "",
        val optText: String = "",
        val isOrderBtnLoading: Boolean = false,
        val isConfirmBtnLoading: Boolean = false,
    )
}