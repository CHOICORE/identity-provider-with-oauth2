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

module(name = ":identity-provider", projectDir = "api/identity-provider")
module(name = ":relying-party", projectDir = "api/relying-party")
module(name = ":oauth2-authorization", projectDir = "modules/oauth2-authorization")
module(name = ":core:user", projectDir = "modules/core/user")

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

modules.forEach { module: Module ->
    include(module.name)
    project(module.name).projectDir = file(module.projectDir)
}

rootProject.name = "like-a-puppy"
