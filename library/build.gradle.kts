plugins {
    id(libs.plugins.commonMppLib.get().pluginId)
    id(libs.plugins.mavenPublish.get().pluginId)
}
version = project.property("library.version") as String

android {
    namespace = project.property("library.namespace") as String
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
            url = uri(project.property("library.url") as String)
            credentials {
                username =
                    project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME_GITHUB")
                password =
                    project.findProperty("gpr.token") as String? ?: System.getenv("TOKEN_GITHUB")
            }
        }
    }
    publications {
        create<MavenPublication>("multiplatform") {
            groupId = project.property("library.groupId") as String
            artifactId = project.property("library.artifactId") as String
            from(components["kotlin"])
        }
    }
}
