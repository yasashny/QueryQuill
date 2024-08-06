pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "QueryQuill"
include(":app")
include(":feature:settings")
include(":data:settings")
include(":data:requests")
include(":core:ui")
include(":core:model")
include(":feature:response")
include(":core:utils")
