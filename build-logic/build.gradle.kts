plugins {
    `kotlin-dsl`
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("commonMppLib") {
            id = "common.mpp.lib"
            implementationClass = "plugins.CommonMppLibPlugin"
        }
    }
}
