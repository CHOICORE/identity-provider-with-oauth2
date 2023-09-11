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
	val lombok: Provider<MinimalExternalModuleDependency> = libs.findLibrary("lombok").get()
	compileOnly(lombok)
	annotationProcessor(lombok)
}