package com.sdk.supergo.ui.human

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.sdk.supergo.core.CityType
import com.sdk.supergo.data.model.Car
import com.sdk.supergo.data.model.CityItem
import com.sdk.supergo.data.model.Order
import com.sdk.supergo.data.model.Region
import com.sdk.supergo.data.repository.NetworkRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

val FakeCityItem = CityItem(
    id = 0,
    name = "Shaharni tanlang",
    region = Region(
        id = 1,
        name = "Shaharni tanlang"
    )
)

internal class HumanStoreFactory(
    private val storeFactory: StoreFactory
) : KoinComponent {

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
        data class OnOtpChanged(val value: String) : Message
        data object OnCloseConfirm : Message
        data object OnBackToOrder : Message
        data object OnShowConfirm : Message
        data object OnOrderBtnStateChanged : Message
        data object OnConfirmBtnStateChanged : Message
        data class OnSuccessChange(val success: Boolean) : Message
        data class OnSuccess(
            val cityList1: List<CityItem>,
            val cityList2: List<CityItem>,
            val carList: List<Car>
        ) : Message
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<HumanStore.Intent, Unit, HumanStore.State, Message, Nothing>(
           // appDispatchers.main
        ) {
        override fun executeAction(action: Unit, getState: () -> HumanStore.State) {
            loadMainScreenData()
        }

        override fun executeIntent(intent: HumanStore.Intent, getState: () -> HumanStore.State) {
            when (intent) {
                is HumanStore.Intent.OnNumberChanged -> dispatch(Message.OnNumberChanged(intent.value))
                is HumanStore.Intent.OnSendCode -> sendCode(getState().number)
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
                is HumanStore.Intent.OnCloseOrder -> dispatch(Message.OnCloseOrder)
                is HumanStore.Intent.OnShowOrder -> dispatch(Message.OnShowOrder)
                is HumanStore.Intent.OnCloseConfirm -> dispatch(Message.OnCloseConfirm)
                is HumanStore.Intent.OnBackToOrder -> dispatch(Message.OnBackToOrder)
                is HumanStore.Intent.OnConfirmClicked -> order(getState())
                is HumanStore.Intent.OnOtpChanged -> dispatch(Message.OnOtpChanged(intent.value))
            }
        }

        private fun order(state: HumanStore.State) {
            dispatch(Message.OnConfirmBtnStateChanged)
            scope.launch {
                val response = repository.sendOrder(
                    Order(
                        phone = state.number,
                        code = state.optText,
                        where = state.selectedCity1.id.toString(),
                        whereTo = state.selectedCity2.id.toString(),
                        person = state.peopleCount.toInt(),
                        baggage = state.luggage,
                        bigBaggage = state.largeL,
                        conditioner = state.con,
                        carId = state.carList[state.selectedCarIndex].id.toString(),
                        comment = state.noteToDriver
                    )
                )
                delay(1000L)
                response.collectLatest {
                    dispatch(Message.OnConfirmBtnStateChanged)
                    dispatch(Message.OnSuccessChange(it))
                    if (it)
                        dispatch(Message.OnCloseConfirm)
                }
            }
        }

        private fun sendCode(code: String) {
            dispatch(Message.OnOrderBtnStateChanged)
            scope.launch {
                val response = repository.sendPhoneNumber(code)
                response.collectLatest {
                    delay(800L)
                    dispatch(Message.OnOrderBtnStateChanged)
                    if (!it) return@collectLatest
                    dispatch(Message.OnCloseOrder)
                    dispatch(Message.OnShowConfirm)
                }
            }
        }

        private fun loadMainScreenData() {
            scope.launch {
                val cityList1 = mutableListOf<CityItem>()
                val cityList2 = mutableListOf<CityItem>()
                val carList = mutableListOf<Car>()
                repository.getCityList(CityType.ANDIJON)
                    .catch {

                    }
                    .collectLatest { list ->
                        cityList1.clear()
                        cityList1.addAll(list)
                    }
                repository.getCityList(CityType.TOSHKENT)
                    .catch {

                    }.collectLatest { list ->
                        cityList2.clear()
                        cityList2.addAll(list)
                    }
                repository.getCarList()
                    .catch {

                    }.collectLatest { list ->
                        carList.clear()
                        carList.addAll(list)
                    }
                dispatch(Message.OnSuccess(cityList1, cityList2, carList))
            }
        }
    }

    private object ReducerImpl : Reducer<HumanStore.State, Message> {
        override fun HumanStore.State.reduce(msg: Message): HumanStore.State {
            return when (msg) {
                is Message.OnShowConfirm -> copy(isConfirmVisible = true)
                is Message.OnSuccessChange -> copy(isSuccess = msg.success)
                is Message.OnOrderBtnStateChanged -> copy(isOrderBtnLoading = !isOrderBtnLoading)
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
                is Message.OnShowOrder -> copy(isOrderVisible = true, isSuccess = null)
                is Message.OnBackToOrder -> copy(
                    isConfirmVisible = false,
                    isOrderVisible = true,
                    optText = ""
                )

                is Message.OnCloseConfirm -> copy(isConfirmVisible = false, number = "", optText = "")
                is Message.OnConfirmBtnStateChanged -> copy(isConfirmBtnLoading = !isConfirmBtnLoading)
                is Message.OnOtpChanged -> copy(optText = msg.value, isSuccess = null)
                is Message.OnSuccess -> copy(
                    isLoading = false,
                    cityList1 = msg.cityList1,
                    cityList2 = msg.cityList2,
                    carList = msg.carList,
                    selectedCity1 = msg.cityList1.firstOrNull() ?: FakeCityItem,
                    selectedCity2 = msg.cityList2.firstOrNull() ?: FakeCityItem
                )
            }
        }
    }
}