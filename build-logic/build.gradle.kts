import org.gradle.accessors.dm.LibrariesForLibs

val libraries: LibrariesForLibs = libs

plugins {
	`kotlin-dsl`
	alias(libs.plugins.ktlint)
}

dependencies {
	implementation(libs.gradlePlugin.springBoot)
	implementation(libs.gradlePlugin.kotlin)
	implementation(libs.gradlePlugin.ktlint)
	implementation(libs.kotlin.reflect)
	implementation(libs.kotlin.allopen)
	implementation(libs.kotlin.noarg)
}