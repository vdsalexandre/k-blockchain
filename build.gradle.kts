import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val assertkVersion: String = "0.25"

plugins {
    kotlin("jvm") version "1.7.10"
}

group = "wishow.vds.com"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("com.willowtreeapps.assertk:assertk:$assertkVersion")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}