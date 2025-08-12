plugins {
    kotlin("jvm") version "2.2.0"
}

group = "com.ll"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.assertj:assertj-core:3.27.3")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.3")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.3")
}

tasks.test {
    useJUnitPlatform()
    testClassesDirs = files("build/classes/kotlin/test")
    classpath = files("build/classes/kotlin/test") + sourceSets.test.get().runtimeClasspath
    testLogging {
        events("passed", "skipped", "failed")
    }
}

kotlin {
    jvmToolchain(23)
}