package com.sdk.supergo

class IOSPlatform: Platform {
    override val name: PlatformName
        get() = PlatformName.IOS
}

actual fun getPlatform(): Platform = IOSPlatform()