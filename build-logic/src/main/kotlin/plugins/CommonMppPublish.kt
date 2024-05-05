package plugins

import extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.named
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

open class PublishExtension {
    lateinit var url: String
    lateinit var groupId: String
    lateinit var artifactId: String
}

class CommonMppPublish : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            val publishExtension =
                extensions.create("publishConfig", PublishExtension::class.java)

            with(pluginManager) {
                apply(libs.findPlugin("mavenPublish").get().get().pluginId)
            }
            afterEvaluate {
                configure<PublishingExtension> {
                    repositories {
                        maven {
                            url = uri(publishExtension.url)
                            credentials {
                                username = findProperty("gpr.user") as String?
                                    ?: System.getenv("USERNAME_GITHUB")
                                password = findProperty("gpr.token") as String?
                                    ?: System.getenv("TOKEN_GITHUB")
                            }
                        }
                    }
                    publications {
                        named<MavenPublication>("kotlinMultiplatform") {
                            groupId = publishExtension.groupId
                            artifactId = publishExtension.artifactId
                            pom {
                                name.set(rootProject.name)
                                description.set(findProperty("publicationDescriptionLibrary") as String)
                                url.set(findProperty("publicationUrl") as String)

                                licenses {
                                    license {
                                        name.set(findProperty("publicationLicenseName") as String)
                                        url.set(findProperty("publicationLicenseUrl") as String)
                                    }
                                }

                                scm {
                                    url.set(findProperty("publicationScmUrl") as String)
                                    connection.set(findProperty("publicationScmConnection") as String)
                                    developerConnection.set(findProperty("publicationScmDeveloperConnection") as String)
                                }

                                developers {
                                    developer {
                                        id.set(findProperty("publicationDeveloperId") as String)
                                        name.set(findProperty("publicationDeveloperName") as String)
                                    }
                                }
                            }
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
