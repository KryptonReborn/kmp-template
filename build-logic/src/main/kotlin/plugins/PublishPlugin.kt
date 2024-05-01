package plugins

import extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.get
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

open class PublishExtension {
    lateinit var name: String
    lateinit var url: String
    lateinit var groupId: String
    lateinit var artifactId: String
}

class PublishPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            val publishExtension =
                project.extensions.create("publishConfig", PublishExtension::class.java)

            with(pluginManager) {
                apply(libs.findPlugin("kotlinMultiplatform").get().get().pluginId)
                apply(libs.findPlugin("mavenPublish").get().get().pluginId)
            }
            project.afterEvaluate {
                configure<PublishingExtension> {
                    repositories {
                        maven {
                            url = uri(publishExtension.url)
                            credentials {
                                username =
                                    project.findProperty("gpr.user") as String? ?: System.getenv(
                                        "USERNAME_GITHUB"
                                    )
                                password = project.findProperty("gpr.token") as String?
                                    ?: System.getenv("TOKEN_GITHUB")
                            }
                        }
                    }
                    publications {
                        create<MavenPublication>(publishExtension.name) {
                            groupId = publishExtension.groupId
                            artifactId = publishExtension.artifactId
                            from(components["kotlin"])
                        }
                    }
                }
                configure<KotlinMultiplatformExtension> {
                    androidTarget {
                        publishLibraryVariants("release", "debug")
                        publishLibraryVariantsGroupedByFlavor = true
                    }
                }
            }
        }
    }
}
