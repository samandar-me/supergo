package com.sdk.supergo.data.model

import kotlinx.serialization.Serializable


@Serializable
data class CityItem(
    val id: Int,
    val name: String,
    val region: Region
)
