package com.sdk.supergo.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Region(
    val id: Int,
    val name: String
)