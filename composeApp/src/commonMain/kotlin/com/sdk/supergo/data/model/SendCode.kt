package com.sdk.supergo.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SendCode(
    val phone: String
)
@Serializable
data class CodeResponse(
    val ok: Boolean,
    val data: String
)