import org.jlleitschuh.gradle.ktlint.KtlintExtension

plugins {
    kotlin("jvm")
    kotlin("plugin.spring")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("org.jlleitschuh.gradle.ktlint")
}

group = "com.matijacvetan"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    // implementation("org.springframework.cloud:spring-cloud-starter-vault-config")
    implementation("io.github.oshai:kotlin-logging-jvm:${property("kotlinLoggingJvmVersion")}")
    implementation("org.springdoc:springdoc-openapi-starter-common:${property("openApiStarterCommonVersion")}")

    // reports
    implementation("net.sf.jasperreports:jasperreports:${property("jasperReportsVersion")}") {
        exclude(group = "commons-logging", module = "commons-logging")
    }
    implementation("net.sf.jasperreports:jasperreports-pdf:${property("jasperReportsPdfVersion")}") {
        exclude(group = "commons-logging", module = "commons-logging")
    }
    implementation(files("libs/RobotoFontFamily.jar"))

    developmentOnly("org.springframework.boot:spring-boot-devtools")
    developmentOnly("org.springframework.boot:spring-boot-docker-compose")

    // runtimeOnly("org.postgresql:postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    // testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.testcontainers:junit-jupiter")
    // testImplementation("org.testcontainers:postgresql")
    // testImplementation("org.testcontainers:vault")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xcontext-receivers", "-Xmulti-dollar-interpolation")
    }
}

configure<KtlintExtension> {
    version.set("1.5.0")
}

//  tasks.withType<Test> {
//    useJUnitPlatform()
//  }
