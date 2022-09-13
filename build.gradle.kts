import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm") version "1.7.0"
    kotlin("plugin.serialization") version "1.7.0"
}

group = "com.plainconcepts"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.1.1")
    implementation("io.ktor:ktor-server-content-negotiation:2.1.1")
    implementation("io.ktor:ktor-server-core:2.1.1")
    implementation("io.ktor:ktor-server-netty:2.1.1")
    implementation("io.ktor:ktor-server-resources:2.1.1")

    implementation("org.jetbrains.exposed:exposed-core:0.39.2")
    implementation("org.jetbrains.exposed:exposed-dao:0.39.2")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.39.2")
    implementation("org.jetbrains.exposed:exposed-java-time:0.39.2")
    implementation("org.postgresql:postgresql:42.5.0")
    implementation("org.flywaydb:flyway-core:9.3.0")
    implementation("com.zaxxer:HikariCP:5.0.1")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.0")
    implementation("ch.qos.logback:logback-classic:1.4.0")

    testImplementation(kotlin("test"))
    testImplementation("io.ktor:ktor-server-tests:2.1.1")
    testImplementation("io.ktor:ktor-server-test-host:2.1.1")
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testImplementation("com.h2database:h2:2.1.214")
    testImplementation("io.ktor:ktor-client-content-negotiation:2.1.1")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("com.plainconcepts.hello.MainKt")
}