package plugins.bases

val libs: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

plugins {
	id("plugins.bases.jvm-base")
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

dependencies {
	compileOnly(libs.findLibrary("lombok").get())
}