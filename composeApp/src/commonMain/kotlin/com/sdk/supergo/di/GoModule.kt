package com.sdk.supergo.di

import com.sdk.supergo.data.repository.NetworkRepository
import com.sdk.supergo.data.repository.NetworkService
import com.sdk.supergo.util.logDe
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

//expect fun

fun initKoin(appDeclaration: KoinAppDeclaration = {}) {
    startKoin {
        appDeclaration()
        modules(
            goModule
        )
    }
}

val goModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    useAlternativeNames = false
                })
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        logDe(message)
                    }
                }
                level = LogLevel.ALL
            }
        }
    }
    factory<NetworkRepository> {
        NetworkService(get())
    }
}