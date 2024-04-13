import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
}

enum class HostOs {
    LINUX, WINDOWS, MAC
}

fun getHostOsName(): HostOs {
    val target = System.getProperty("os.name")
    if (target == "Linux") return HostOs.LINUX
    if (target.startsWith("Windows")) return HostOs.WINDOWS
    if (target.startsWith("Mac")) return HostOs.MAC
    throw GradleException("Unknown OS: $target")
}

kotlin {
    val hostOs = getHostOsName()
    println("Host os name $hostOs")

    when (hostOs) {
        HostOs.LINUX -> linuxX64("native")
        HostOs.MAC -> macosX64("native")
        HostOs.WINDOWS -> mingwX64("native")
    }
    jvm()
    js {
        compilations {
            nodejs()
            browser {
                testTask {
                    useKarma {
                        useChromeHeadless()
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }
    androidTarget {
        publishLibraryVariants("release")
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    iosX64("ios")
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                //put your multiplatform dependencies here
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
        val jvmMain by getting {
            dependencies {
                //put your multiplatform dependencies here
            }
        }
        val jvmTest by getting {
            dependencies {
                //put your multiplatform dependencies here
            }
        }
        val jsMain by getting {
            dependencies {
                //put your multiplatform dependencies here
            }
        }
        val jsTest by getting {
            dependencies {
                //put your multiplatform dependencies here
            }
        }
        val wasmJsMain by getting {
            dependencies {
                //put your multiplatform dependencies here
            }
        }
        val wasmJsTest by getting {
            dependencies {
                //put your multiplatform dependencies here
            }
        }
        val nativeMain by getting {
            dependsOn(commonMain)
        }
        val nativeTest by getting {
            dependsOn(commonTest)
        }
        val iosMain by getting {
            dependsOn(nativeMain)
        }
        val iosTest by getting {
            dependsOn(nativeTest)
        }
        val iosArm64Main by getting {
            dependsOn(nativeMain)
        }
        val iosArm64Test by getting {
            dependsOn(nativeTest)
        }
        val iosSimulatorArm64Main by getting {
            dependsOn(nativeMain)
        }
        val iosSimulatorArm64Test by getting {
            dependsOn(nativeTest)
        }
    }
}

android {
    namespace = "org.jetbrains.kotlinx.kmp.template"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
