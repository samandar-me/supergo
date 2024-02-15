package com.sdk.supergo

interface Platform {
    val name: PlatformName
}

expect fun getPlatform(): Platform

enum class PlatformName {
    ANDROID,IOS
}