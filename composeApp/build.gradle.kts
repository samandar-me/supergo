plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose)
    alias(libs.plugins.android.application)
   // alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.sqlDelight)
    id("kotlin-parcelize")
    kotlin("plugin.serialization") version "1.6.21"
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true

            export("com.arkivanov.decompose:decompose:2.1.0-compose-experimental-alpha-07")
            export("com.arkivanov.essenty:lifecycle:1.2.0-alpha-06")
        }
    }

    sourceSets {
        all {
            languageSettings {
                optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
            }
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)
//            implementation(libs.decompose)
//            implementation(libs.decompose.compose)
            implementation(libs.composeImageLoader)
            implementation(libs.napier)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.core)
            implementation(libs.composeIcons.featherIcons)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.datetime)
            implementation(libs.koin.core)

            api("com.arkivanov.decompose:decompose:2.1.0-compose-experimental-alpha-07")
            api("com.arkivanov.decompose:extensions-compose-jetbrains:2.1.0-compose-experimental-alpha-07")
            implementation("com.arkivanov.essenty:lifecycle:1.2.0-alpha-06")

            api(libs.mvikotlin)
            api(libs.mvikotlin.main)
            api(libs.mvikotlin.extensions.coroutines)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }

        androidMain.dependencies {
            implementation(libs.androidx.appcompat)
            implementation(libs.androidx.activityCompose)
            implementation(libs.compose.uitooling)
            implementation(libs.kotlinx.coroutines.android)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.sqlDelight.driver.android)
            implementation(libs.koin.core)
            implementation(libs.koin.android)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation(libs.sqlDelight.driver.native)
        }
    }
}

android {
    namespace = "com.sdk.supergo"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        targetSdk = 34

        applicationId = "com.sdk.supergo.androidApp"
        versionCode = 1
        versionName = "1.0.0"
    }
    sourceSets["main"].apply {
        manifest.srcFile("src/androidMain/AndroidManifest.xml")
        res.srcDirs("src/androidMain/resources")
        resources.srcDirs("src/commonMain/resources")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }
}

sqldelight {
    databases {
        create("MyDatabase") {
            // Database configuration here.
            // https://cashapp.github.io/sqldelight
            packageName.set("com.sdk.supergo.db")
        }
    }
}
