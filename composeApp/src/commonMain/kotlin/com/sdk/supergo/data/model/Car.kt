package com.sdk.supergo.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Car(
    val count: Int,
    val icon: String,
    val id: Int,
    val name: String,
    val price: Double?
)