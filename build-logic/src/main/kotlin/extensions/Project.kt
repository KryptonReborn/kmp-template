package extensions

import org.gradle.api.Project
import org.gradle.api.artifacts.*
import org.gradle.kotlin.dsl.getByType

val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")
