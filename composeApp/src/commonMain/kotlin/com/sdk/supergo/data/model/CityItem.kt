package com.sdk.supergo.data.model

import kotlinx.serialization.Serializable


@Serializable
data class CityItem(
    val id: Int,
    val name: String,
    val region: Region
)
val FakeCityItem = CityItem(
    id = 0,
    name = "Shaharni tanlang",
    region = Region(
        id = 1,
        name = "Shaharni tanlang"
    )
)