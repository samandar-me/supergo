package com.sdk.supergo

class AndroidPlatform: Platform {
    override val name: PlatformName
        get() = PlatformName.ANDROID
}
actual fun getPlatform(): Platform = AndroidPlatform()