dependencyResolutionManagement {
	includeBuild("build-logic")

	@Suppress("UnstableApiUsage")
	repositoriesMode
		.apply { set(RepositoriesMode.FAIL_ON_PROJECT_REPOS) }
	@Suppress("UnstableApiUsage")
	repositories {
		mavenCentral()
		gradlePluginPortal()
	}
}

private val modules: MutableList<Module> = mutableListOf()

module(name = ":idp", projectDir = "api/identity-provider")

private data class Module(
	val name: String,
	val projectDir: String,
)

fun module(name: String, projectDir: String) {
	modules.add(
		Module(
			name = name,
			projectDir = "$rootDir/$projectDir",
		),
	)
}

modules.forEach { module ->
	include(module.name)
	project(module.name).projectDir = file(module.projectDir)
}

rootProject.name = "like-a-puppy"