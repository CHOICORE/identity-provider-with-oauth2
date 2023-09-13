package plugins.bases

import plugins.CheckstyleUtil

val libs: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

plugins {
    id("plugins.bases.base")
    java
    checkstyle
}

dependencies {
    testRuntimeOnly(libs.findLibrary("junit-platform-launcher").get())
    testImplementation(libs.findBundle("junit").get())
}

java {
    /**
     * Gets the project wide toolchain requirements that will be used for tasks requiring a tool from the toolchain (e.g. JavaCompile).
     */
    toolchain {
        languageVersion.set(JavaLanguageVersion.of("${libs.findVersion("jdk").get()}"))
    }
    /**
     * Adds a task sourcesJar that will package the Java sources of the main SourceSet in a JAR with classifier sources.
     */
    withSourcesJar()
    /**
     * Adds a task javadocJar that will package the output of the javadoc task in a JAR with classifier javadoc.
     */
    withJavadocJar()
}

checkstyle {
    config = resources.text.fromString(CheckstyleUtil.getCheckstyleConfig("/checkstyle.xml"))
    maxWarnings = 0
}

/**
 * Enable deprecation messages when compiling Java code
 */
tasks {
    withType<JavaCompile>().configureEach {
        options.compilerArgs.add("-Xlint:deprecation")
    }
    withType<Checkstyle> {
        enabled = true
    }
}
