import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnLockMismatchReport
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension
import javax.xml.parsers.DocumentBuilderFactory

plugins {
    id(libs.plugins.commonMppLib.get().pluginId)
    id(libs.plugins.commonMppPublish.get().pluginId)
    id(libs.plugins.kover.get().pluginId)
}

publishConfig {
    url = "https://maven.pkg.github.com/KryptonReborn/kmp-template"
    groupId = "kmp.template"
    artifactId = "library"
}

version = "0.0.2"

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

tasks.dokkaHtml {
    outputDirectory.set(layout.buildDirectory.dir("documentation/html"))
}

tasks.register("printLineCoverage") {
    group = "verification" // Put into the same group as the `kover` tasks
    dependsOn("koverXmlReport")
    doLast {
        val report = file("$projectDir/build/reports/kover/report.xml")

        val doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(report)
        val rootNode = doc.firstChild
        var childNode = rootNode.firstChild

        var coveragePercent = 0.0

        while (childNode != null) {
            if (childNode.nodeName == "counter") {
                val typeAttr = childNode.attributes.getNamedItem("type")
                if (typeAttr.textContent == "LINE") {
                    val missedAttr = childNode.attributes.getNamedItem("missed")
                    val coveredAttr = childNode.attributes.getNamedItem("covered")

                    val missed = missedAttr.textContent.toLong()
                    val covered = coveredAttr.textContent.toLong()

                    coveragePercent = (covered * 100.0) / (missed + covered)

                    break
                }
            }
            childNode = childNode.nextSibling
        }

        println("%.1f".format(coveragePercent))
    }
}

rootProject.plugins.withType<YarnPlugin> {
    rootProject.configure<YarnRootExtension> {
        yarnLockMismatchReport = YarnLockMismatchReport.WARNING
        yarnLockAutoReplace = true
    }
}
