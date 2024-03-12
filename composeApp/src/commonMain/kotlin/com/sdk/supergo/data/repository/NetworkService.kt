package com.sdk.supergo.data.repository

import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import com.sdk.supergo.core.CityType
import com.sdk.supergo.data.model.Car
import com.sdk.supergo.data.model.CityItem
import com.sdk.supergo.data.model.CodeResponse
import com.sdk.supergo.data.model.SendCode
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.FormPart
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.Parameters
import io.ktor.http.contentType
import io.ktor.util.InternalAPI
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

    override suspend fun getCarList(): Flow<List<Car>> = flow {
        val response = httpClient.get(BASE_URL + "get-cars").body<List<Car>>()
        emit(response)
    }

    override suspend fun sendPhoneNumber(number: String): Flow<Boolean> = flow {
        val response = httpClient.submitForm(
            url = BASE_URL + "send-code/",
            formParameters = Parameters.build {
                append("phone", number)
            }
        )
        emit(response.status == HttpStatusCode.OK)
    }
}