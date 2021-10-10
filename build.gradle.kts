plugins {
    kotlin("jvm") version "1.4.21"
    kotlin("plugin.spring") version "1.4.21"

    id("org.springframework.boot") version "2.4.4"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
}

group = "com.justai.jaicf"
version = "1.0.0"

val jaicf = "1.2.0"
val logback = "1.2.3"

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    implementation("com.just-ai.jaicf:core:$jaicf")
    implementation("com.just-ai.jaicf:jaicp:$jaicf")
    implementation("com.just-ai.jaicf:caila:$jaicf")
    implementation("com.just-ai.jaicf:mongo:$jaicf")

    implementation("javax.servlet:javax.servlet-api:3.1.0")
    implementation("io.micrometer:micrometer-registry-prometheus:1.7.4")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    bootJar {
        archiveFileName.set("app.jar")
        mainClass.set("com.justai.jaicf.spring.ApplicationKt")
    }
}

tasks.create("stage") {
    dependsOn("bootJar")
}