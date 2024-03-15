package com.sdk.supergo.data.model

data class Order(
    val phone: String,
    val code: String,
    val where: String,
    val whereTo: String,
    val person: Int,
    val baggage: Boolean,
    val bigBaggage: Boolean,
    val conditioner: Boolean,
    val carId: String,
    val comment: String
)
data class OrderDeliver(
    val phone: String,
    val code: String,
    val where: String,
    val whereTo: String,
    val comment: String
)