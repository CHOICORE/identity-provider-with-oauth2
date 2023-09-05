package plugins.bases


import org.jetbrains.kotlin.gradle.dsl.jvm.JvmTargetValidationMode.WARNING
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val libs: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")
val jdk: VersionConstraint = libs.findVersion("jdk").get()

plugins {
	id("plugins.bases.jvm-base")
	id("org.jlleitschuh.gradle.ktlint")
	kotlin("jvm")
}

dependencies {
}

kotlin {
	jvmToolchain {
		languageVersion.set(JavaLanguageVersion.of("$jdk"))
	}

	sourceSets {
		named("main") {
			kotlin.srcDir("src/main/kotlin")
		}
		named("test") {
			kotlin.srcDir("src/test/kotlin")
		}
	}
}

tasks {
	withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs = listOf(
				"-Xjsr305=strict",
			)
			jvmTarget = "$jdk"
		}

		/**
		 * error – the plugin fails the build; the default value for projects on Gradle 8.0+.
		 *
		 * warning – the plugin prints a warning message; the default value for projects on Gradle less than 8.0.
		 *
		 * ignore – the plugin skips the check and doesn't produce any messages.
		 */
		jvmTargetValidationMode.set(WARNING)
	}
}