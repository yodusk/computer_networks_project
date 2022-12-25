plugins {
    id("org.jetbrains.kotlin.jvm") version "1.6.21"
    id("org.jetbrains.kotlin.kapt") version "1.6.21"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.6.21"
    id("org.jetbrains.kotlin.plugin.jpa") version "1.6.21"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("io.micronaut.application") version "3.6.3"
    id("io.micronaut.test-resources") version "3.6.3"
}

version = "0.1"
group = "com.app"

val kotlinVersion = project.properties.get("kotlinVersion")
repositories {
    mavenCentral()
}

dependencies {
    kapt("io.micronaut.data:micronaut-data-processor")
    kapt("io.micronaut:micronaut-http-validation")
    kapt("io.micronaut.openapi:micronaut-openapi")
    kapt("io.micronaut.serde:micronaut-serde-processor")
    implementation("io.reactivex.rxjava2:rxjava:2.2.8")
    implementation("org.apache.commons:commons-text:1.10.0")
    implementation("com.lordcodes.turtle:turtle:0.5.0")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut.data:micronaut-data-hibernate-jpa")
    implementation("io.micronaut.flyway:micronaut-flyway")
    implementation("io.micronaut.kotlin:micronaut-kotlin-extension-functions")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("io.micronaut.neo4j:micronaut-neo4j-bolt:5.0.0")
    implementation("io.micronaut.problem:micronaut-problem-json")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("io.micronaut.sql:micronaut-jdbc-hikari")
    implementation("io.swagger.core.v3:swagger-annotations")
    implementation("jakarta.annotation:jakarta.annotation-api")
    annotationProcessor("io.micronaut:micronaut-management")
    implementation("io.micronaut:micronaut-management")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    implementation("commons-logging:commons-logging:1.2")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("org.slf4j:slf4j-simple")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("io.micronaut:micronaut-validation")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
    testRuntimeOnly("org.neo4j.test:neo4j-harness")
}

application {
    mainClass.set("com.app.ApplicationKt")
}
java {
    sourceCompatibility = JavaVersion.toVersion("11")
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
}
graalvmNative.toolchainDetection.set(false)
micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("com.app.*")
    }
}
