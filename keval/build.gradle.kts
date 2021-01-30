/*
 * @notKamui
 *
 * Gradle build file for Keval
 */

import java.net.URL

version = "0.5"

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.4.20"
    id("org.jetbrains.dokka") version "1.4.20"
    `java-library`
}

repositories {
    jcenter()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.google.guava:guava:29.0-jre")
    implementation("io.github.classgraph:classgraph:4.8.102")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    api("org.apache.commons:commons-math3:3.6.1")
}

tasks.jar {
    manifest {
        attributes(
            mapOf(
                "Implementation-Title" to project.name,
                "Implementation-Version" to project.version
            )
        )
    }
}

java {
    withSourcesJar()
}

tasks.dokkaHtml.configure {
    outputDirectory.set(rootDir.resolve("docs"))
    moduleName.set("Keval")
    dokkaSourceSets {
        configureEach {
            sourceLink {
                localDirectory.set(file("src/main/kotlin"))
                remoteUrl.set(
                    URL(
                        "https://github.com/notKamui/Keval/tree/main/keval/src/main/kotlin"
                    )
                )
                remoteLineSuffix.set("#L")
            }
            jdkVersion.set(8)
        }
    }
}