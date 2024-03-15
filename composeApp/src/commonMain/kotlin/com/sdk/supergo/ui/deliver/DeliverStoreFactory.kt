package com.sdk.supergo.ui.deliver

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.sdk.supergo.core.CityType
import com.sdk.supergo.data.model.Car
import com.sdk.supergo.data.model.CityItem
import com.sdk.supergo.data.model.Order
import com.sdk.supergo.data.model.OrderDeliver
import com.sdk.supergo.data.model.Region
import com.sdk.supergo.data.repository.NetworkRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class HumanStoreFactory(
    private val storeFactory: StoreFactory
) : KoinComponent {

    private val repository: NetworkRepository by inject()

    fun create(): DeliverStore = object : DeliverStore,
        Store<DeliverStore.Intent, DeliverStore.State, Nothing> by storeFactory.create(
            name = "HumanStore",
            initialState = DeliverStore.State(),
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
        data object OnOrderClicked : Message
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
        CoroutineExecutor<DeliverStore.Intent, Unit, DeliverStore.State, Message, Nothing>(
           // appDispatchers.main
        ) {
        override fun executeAction(action: Unit, getState: () -> DeliverStore.State) {
            loadMainScreenData()
        }

        override fun executeIntent(intent: DeliverStore.Intent, getState: () -> DeliverStore.State) {
            when (intent) {
                is DeliverStore.Intent.OnNumberChanged -> dispatch(Message.OnNumberChanged(intent.value))
                is DeliverStore.Intent.OnSendCode -> sendCode(getState().number)
                is DeliverStore.Intent.OnFromChanged -> dispatch(Message.OnFromChanged)
                is DeliverStore.Intent.OnToChanged -> dispatch(Message.OnToChanged)
                is DeliverStore.Intent.OnFromSelected -> dispatch(Message.OnFromSelected(intent.city))
                is DeliverStore.Intent.OnToSelected -> dispatch(Message.OnToSelected(intent.city))
                is DeliverStore.Intent.OnReplaced -> dispatch(Message.OnReplaced)
                is DeliverStore.Intent.OnOrderClicked -> dispatch(Message.OnOrderClicked)
                is DeliverStore.Intent.OnNoteChanged -> dispatch(Message.OnNoteChanged(intent.note))
                is DeliverStore.Intent.OnCloseOrder -> dispatch(Message.OnCloseOrder)
                is DeliverStore.Intent.OnShowOrder -> dispatch(Message.OnShowOrder)
                is DeliverStore.Intent.OnCloseConfirm -> dispatch(Message.OnCloseConfirm)
                is DeliverStore.Intent.OnBackToOrder -> dispatch(Message.OnBackToOrder)
                is DeliverStore.Intent.OnConfirmClicked -> order(getState())
                is DeliverStore.Intent.OnOtpChanged -> dispatch(Message.OnOtpChanged(intent.value))
            }
        }

        private fun order(state: DeliverStore.State) {
            dispatch(Message.OnConfirmBtnStateChanged)
            scope.launch {
                val response = repository.sendDeliverOrder(
                    OrderDeliver(
                        phone = state.number,
                        code = state.optText,
                        where = state.selectedCity1.id.toString(),
                        whereTo = state.selectedCity2.id.toString(),
                        isDelivery = true,
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

    private object ReducerImpl : Reducer<DeliverStore.State, Message> {
        override fun DeliverStore.State.reduce(msg: Message): DeliverStore.State {
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
                    cityList2 = msg.cityList2
                )
            }
        }
    }
}