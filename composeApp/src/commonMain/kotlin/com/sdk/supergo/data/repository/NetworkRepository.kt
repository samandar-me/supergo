package com.sdk.supergo.data.repository

import com.sdk.supergo.core.CityType
import com.sdk.supergo.data.model.CityItem
import kotlinx.coroutines.flow.Flow

interface NetworkRepository {
    suspend fun getCityList(cityType: CityType): Flow<List<CityItem>>
}