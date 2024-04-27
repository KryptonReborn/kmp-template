plugins {
    id(libs.plugins.commonMppLib.get().pluginId)
    id("maven-publish")
}

version = "0.0.1"

android {
    namespace = "kmp.template"
}

kotlin {
    androidTarget {
        publishLibraryVariants("release", "debug")
        publishLibraryVariantsGroupedByFlavor = true
    }
    sourceSets {
        val commonMain by getting {
            dependencies {}
        }
    }
}

publishing {
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/KryptonReborn/kmp-template")
            credentials {
                username =
                    project.findProperty("gpr.user") as? String ?: System.getenv("GITHUB_USERNAME")
                password =
                    project.findProperty("gpr.key") as? String ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
    publications {
        create<MavenPublication>("multiplatform") {
            groupId = "kmp.template"
            artifactId = "library"
            from(components["kotlin"])
        }
    }
}
