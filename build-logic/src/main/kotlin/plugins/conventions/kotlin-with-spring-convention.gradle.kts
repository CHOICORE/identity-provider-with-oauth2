package plugins.conventions

plugins {
	id("plugins.conventions.spring-convention")
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
//	implementation("org.jetbrains.kotlin:kotlin-allopen")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
}