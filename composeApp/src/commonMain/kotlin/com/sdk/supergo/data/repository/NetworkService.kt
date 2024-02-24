package com.sdk.supergo.data.repository

import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import com.sdk.supergo.core.CityType
import com.sdk.supergo.data.model.CityItem
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

private const val BASE_URL = "https://api.supergo.uz/api/"

internal class NetworkService(
    private val httpClient: HttpClient
) : NetworkRepository {
    override suspend fun getCityList(cityType: CityType): Flow<List<CityItem>> = flow {
        val response = httpClient.get(BASE_URL + "get-district/") {
            parameter("region", cityType.name.lowercase().capitalize(Locale.current))
        }.body<List<CityItem>>()
        emit(response)
    }
}