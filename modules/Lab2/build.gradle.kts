plugins {
    id("java")
    id("application")
}

group = "study"
version = "0.1.0"

java {
    toolchain { languageVersion = JavaLanguageVersion.of(21) }
}

tasks.withType<JavaCompile>().configureEach { options.encoding = "UTF-8" }

repositories { mavenCentral() }

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.4")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
    testLogging { events("passed", "failed", "skipped") }
}

application {
    mainClass.set("lab2.Main")
}

tasks.named<JavaExec>("run") {
    jvmArgs("-Dfile.encoding=UTF-8")
}