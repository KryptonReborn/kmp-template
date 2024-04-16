package plugins

import com.android.build.gradle.LibraryExtension
import extensions.libs
import org.gradle.api.*
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

class CommonMppLibPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.findPlugin("androidLibrary").get().get().pluginId)
                apply(libs.findPlugin("kotlinMultiplatform").get().get().pluginId)
            }

            extensions.configure<LibraryExtension> {
                compileSdk = libs.findVersion("androidCompileSdk").get().displayName.toInt()
                defaultConfig {
                    minSdk = libs.findVersion("androidMinSdk").get().displayName.toInt()
                }
            }

            extensions.configure<KotlinMultiplatformExtension> {
                val hostOs = getHostOsName()
                println("Host os name $hostOs")

                when (hostOs) {
                    HostOs.LINUX -> linuxX64("native")
                    HostOs.MAC -> macosX64("native")
                    HostOs.WINDOWS -> mingwX64("native")
                }
                jvm()
                js().apply {
                    compilations.apply {
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

                sourceSets.apply {
                    commonMain.get()
                    commonTest.dependencies {
                        implementation(libs.findLibrary("kotlinTest").get())
                    }
                    nativeMain.get().dependsOn(commonMain.get())
                    nativeTest.get().dependsOn(commonTest.get())
                    iosMain.get().dependsOn(nativeMain.get())
                    iosTest.get().dependsOn(nativeTest.get())
                }
            }
        }
    }

    enum class HostOs {
        LINUX, WINDOWS, MAC
    }

    private fun getHostOsName(): HostOs {
        val target = System.getProperty("os.name")
        if (target == "Linux") return HostOs.LINUX
        if (target.startsWith("Windows")) return HostOs.WINDOWS
        if (target.startsWith("Mac")) return HostOs.MAC
        throw GradleException("Unknown OS: $target")
    }
}
