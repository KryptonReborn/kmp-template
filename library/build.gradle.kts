plugins {
    id(libs.plugins.commonMppLib.get().pluginId)
}


kotlin {
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
    }
}
