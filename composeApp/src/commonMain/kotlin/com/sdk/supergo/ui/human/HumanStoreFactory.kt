package com.sdk.supergo.ui.human

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.sdk.supergo.appDispatchers
import com.sdk.supergo.core.CityType
import com.sdk.supergo.data.model.CityItem
import com.sdk.supergo.data.repository.NetworkRepository
import com.sdk.supergo.util.logDe
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class HumanStoreFactory(
    private val storeFactory: StoreFactory
): KoinComponent {

    private val repository: NetworkRepository by inject()

    fun create(): HumanStore = object : HumanStore,
        Store<HumanStore.Intent, HumanStore.State, Nothing> by storeFactory.create(
            name = "HumanStore",
            initialState = HumanStore.State(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed interface Message {
        data object Loading : Message
        data object OnFromChanged : Message
        data object OnToChanged : Message
        data class OnFromSelected(val city: CityItem) : Message
        data class OnToSelected(val city: CityItem) : Message
        data object OnReplaced : Message
        data object OnLuggage : Message
        data object OnLargeL : Message
        data object OnCon : Message
        data object OnOrderClicked : Message
        data class OnPeopleCountChanged(val count: String) : Message
        data class OnCarSelected(val index: Int) : Message
        data class OnNoteChanged(val note: String) : Message
        data object OnCloseOrder : Message
        data object OnShowOrder : Message
        data class OnNumberChanged(val value: String) : Message
        data object OnSendCode : Message
        data object OnCloseConfirm : Message
        data object OnBackToOrder : Message
        data object OnConfirmClicked : Message
        data class OnSuccess(
            val cityList1: List<CityItem>,
            val cityList2: List<CityItem>,
        ): Message
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<HumanStore.Intent, Unit, HumanStore.State, Message, Nothing>(
            appDispatchers.main
        ) {
        override fun executeAction(action: Unit, getState: () -> HumanStore.State) {
            loadMainScreenData()
        }

        override fun executeIntent(intent: HumanStore.Intent, getState: () -> HumanStore.State) {
            when (intent) {
                is HumanStore.Intent.OnFromChanged -> dispatch(Message.OnFromChanged)
                is HumanStore.Intent.OnToChanged -> dispatch(Message.OnToChanged)
                is HumanStore.Intent.OnFromSelected -> dispatch(Message.OnFromSelected(intent.city))
                is HumanStore.Intent.OnToSelected -> dispatch(Message.OnToSelected(intent.city))
                is HumanStore.Intent.OnReplaced -> dispatch(Message.OnReplaced)
                is HumanStore.Intent.OnPeopleCountChanged -> dispatch(
                    Message.OnPeopleCountChanged(
                        intent.count
                    )
                )

                is HumanStore.Intent.OnCarSelected -> dispatch(Message.OnCarSelected(intent.index))
                is HumanStore.Intent.OnLuggage -> dispatch(Message.OnLuggage)
                is HumanStore.Intent.OnLarge -> dispatch(Message.OnLargeL)
                is HumanStore.Intent.OnCon -> dispatch(Message.OnCon)
                is HumanStore.Intent.OnOrderClicked -> dispatch(Message.OnOrderClicked)
                is HumanStore.Intent.OnNoteChanged -> dispatch(Message.OnNoteChanged(intent.note))
                is HumanStore.Intent.OnNumberChanged -> dispatch(Message.OnNumberChanged(intent.value))
                is HumanStore.Intent.OnSendCode -> dispatch(Message.OnSendCode)
                is HumanStore.Intent.OnCloseOrder -> dispatch(Message.OnCloseOrder)
                is HumanStore.Intent.OnShowOrder -> dispatch(Message.OnShowOrder)
                is HumanStore.Intent.OnCloseConfirm -> dispatch(Message.OnCloseConfirm)
                is HumanStore.Intent.OnBackToOrder -> dispatch(Message.OnBackToOrder)
                is HumanStore.Intent.OnConfirmClicked -> dispatch(Message.OnConfirmClicked)
            }
        }
        private fun loadMainScreenData() {
            scope.launch {
                val cityList1 = mutableListOf<CityItem>()
                val cityList2 = mutableListOf<CityItem>()
                repository.getCityList(CityType.ANDIJON)
                    .catch {

                    }
                    .collectLatest { list ->
                        cityList1.clear()
                        cityList1.addAll(list)
                    }
                repository.getCityList(CityType.TOSHKENT)
                    .catch {
                        logDe(it.message.toString())
                    }.collectLatest { list ->
                        cityList2.clear()
                        cityList2.addAll(list)
                    }
                delay(1000L)
                logDe(cityList1.toString())
                dispatch(Message.OnSuccess(cityList1, cityList2))
            }
        }
    }

    private object ReducerImpl : Reducer<HumanStore.State, Message> {
        override fun HumanStore.State.reduce(msg: Message): HumanStore.State {
            return when (msg) {
                is Message.Loading -> copy()
                is Message.OnFromChanged -> copy(fromExpanded = !fromExpanded)
                is Message.OnToChanged -> copy(toExpanded = !toExpanded)
                is Message.OnReplaced -> {
                    val temp = copy()
                    copy(
                        title1 = title2,
                        title2 = temp.title1,
                        fromExpanded = false,
                        toExpanded = false,
                        cityList1 = cityList2,
                        cityList2 = temp.cityList1,
                        selectedCity1 = selectedCity2,
                        selectedCity2 = temp.selectedCity1
                    )
                }

                is Message.OnFromSelected -> copy(
                    fromExpanded = !fromExpanded,
                    selectedCity1 = msg.city
                )

                is Message.OnToSelected -> copy(toExpanded = !toExpanded, selectedCity2 = msg.city)
                is Message.OnPeopleCountChanged -> copy(peopleCount = msg.count)
                is Message.OnCarSelected -> copy(selectedCarIndex = msg.index)
                is Message.OnLargeL -> copy(largeL = !largeL)
                is Message.OnLuggage -> copy(luggage = !luggage)
                is Message.OnCon -> copy(con = !con)
                is Message.OnOrderClicked -> copy()
                is Message.OnNoteChanged -> copy(noteToDriver = msg.note)
                is Message.OnNumberChanged -> copy(number = msg.value)
                is Message.OnCloseOrder -> copy(isOrderVisible = false)
                is Message.OnShowOrder -> copy(isOrderVisible = true)
                is Message.OnSendCode -> copy(isOrderVisible = false, isConfirmVisible = true)
                is Message.OnBackToOrder -> copy(isConfirmVisible = false, isOrderVisible = true)
                is Message.OnCloseConfirm -> copy(isConfirmVisible = false)
                is Message.OnConfirmClicked -> copy(isConfirmVisible = false)
                is Message.OnSuccess -> copy(isLoading = false, cityList1 = msg.cityList1, cityList2 = msg.cityList2, selectedCity1 = msg.cityList1[0], selectedCity2 = msg.cityList2[0])
            }
        }
    }
}