plugins {
    id("org.beryx.runtime") version "2.0.1" apply false
}

subprojects {
    if (!name.startsWith("Lab")) return@subprojects

    apply(plugin = "java")
    apply(plugin = "application")
    apply(plugin = "org.beryx.runtime")

    group = "study"
    version = "0.1.0"

    configure<JavaApplication> {
        mainClass = project.name.lowercase() + ".Main"
    }

    configure<JavaPluginExtension> {
        toolchain { languageVersion = JavaLanguageVersion.of(21) }
    }

    tasks.withType<JavaCompile>().configureEach { options.encoding = "UTF-8" }

    repositories { mavenCentral() }

    dependencies {
        "testImplementation"("org.junit.jupiter:junit-jupiter:5.11.4")
        "testRuntimeOnly"("org.junit.platform:junit-platform-launcher")
        "implementation"("net.sourceforge.pmd:pmd-java:7.28.0")
        "implementation"("ch.qos.logback:logback-classic:1.5.16")
    }

    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
        testLogging { events("passed", "failed", "skipped") }
    }

    configure<org.beryx.runtime.data.RuntimePluginExtension> {
        options = listOf("--strip-debug", "--no-header-files", "--no-man-pages", "--compress", "zip-6")
        jpackage {
            imageOptions = listOf("--win-console")
        }
    }
}