plugins {
    id(libs.plugins.commonMppLib.get().pluginId)
    id(libs.plugins.publish.get().pluginId)
}

publishConfig {
    name = "multiplatform"
    url = "https://maven.pkg.github.com/KryptonReborn/kmp-template"
    groupId = "kmp.template"
    artifactId = "library"
}

version = "0.0.3"

android {
    namespace = "kmp.template"
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {}
        }
    }
}