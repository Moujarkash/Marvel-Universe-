plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    kotlin("plugin.serialization") version "1.8.0"
    id("app.cash.sqldelight") version "2.0.0-alpha05"
}

kotlin {
    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
        }
    }
    
    sourceSets {
        val ktorVersion = "2.2.3"
        val sqldelightVersion = "2.0.0-alpha05"
        val kryptoVersion = "2.2.0"

        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0-RC")
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
                implementation("app.cash.sqldelight:runtime:$sqldelightVersion")
                implementation("app.cash.sqldelight:coroutines-extensions:$sqldelightVersion")
                implementation("com.soywiz.korlibs.krypto:krypto:$kryptoVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-android:$ktorVersion")
                implementation("app.cash.sqldelight:android-driver:$sqldelightVersion")
                implementation("com.soywiz.korlibs.krypto:krypto-android:$kryptoVersion")
            }
        }
        val androidUnitTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)

            dependencies {
                implementation("io.ktor:ktor-client-ios:$ktorVersion")
                implementation("app.cash.sqldelight:native-driver:$sqldelightVersion")
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    namespace = "com.mod.marveluniverse"
    compileSdk = 33
    defaultConfig {
        minSdk = 24
        targetSdk = 33
    }
}

sqldelight {
    databases {
        create("MarvelUniverseDatabase") {
            packageName.set("com.mod.marveluniverse.database")
        }
    }
}