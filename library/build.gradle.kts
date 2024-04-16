plugins {
    id(libs.plugins.commonMppLib.get().pluginId)
}

android {
    namespace = "kmp.template"
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinStdLib)
                implementation(libs.kotlinSerializationJson)
                implementation(libs.kotlinSerializationCbor)
            }
        }
    }
}
