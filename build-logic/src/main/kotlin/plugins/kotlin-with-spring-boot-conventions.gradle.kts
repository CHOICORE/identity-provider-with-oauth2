package plugins


plugins {
	id("plugins.spring-boot-conventions")
	id("plugins.bases.kotlin-base")
	kotlin("plugin.spring")
}

configurations {
	all {
		exclude(group = "org.projectlombok", module = "lombok")
	}
}

dependencies {
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
}