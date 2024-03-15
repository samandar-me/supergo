package com.sdk.supergo.data.repository

import com.sdk.supergo.core.CityType
import com.sdk.supergo.data.model.Car
import com.sdk.supergo.data.model.CityItem
import com.sdk.supergo.data.model.Order
import com.sdk.supergo.data.model.OrderDeliver
import kotlinx.coroutines.flow.Flow

interface NetworkRepository {
    suspend fun getCityList(cityType: CityType): Flow<List<CityItem>>
    suspend fun getCarList(): Flow<List<Car>>
    suspend fun sendPhoneNumber(number: String): Flow<Boolean>
    suspend fun sendOrder(order: Order): Flow<Boolean>
    suspend fun sendDeliverOrder(order: OrderDeliver): Flow<Boolean>
}