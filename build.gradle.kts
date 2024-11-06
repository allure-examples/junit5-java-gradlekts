plugins {
    java
}

tasks.withType(Wrapper::class) {
    gradleVersion = "8.10.2"
}

group = "com.example.junit5"
version = "1.0-SNAPSHOT"

val allureVersion = "2.29.0"
val aspectJVersion = "1.9.22"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks.withType(JavaCompile::class) {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}

val agent: Configuration by configurations.creating {
    isCanBeConsumed = true
    isCanBeResolved = true
}

tasks.test {
    ignoreFailures = true
    useJUnitPlatform()
    jvmArgs = listOf(
        "-javaagent:${agent.singleFile}"
    )
}

dependencies {
    agent("org.aspectj:aspectjweaver:$aspectJVersion")
    
    testImplementation(platform("io.qameta.allure:allure-bom:$allureVersion"))
    testImplementation("io.qameta.allure:allure-junit5")

    testImplementation(platform("org.junit:junit-bom:5.11.3"))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

    testImplementation("org.slf4j:slf4j-simple:2.0.16")
}

repositories {
    mavenCentral()
}
