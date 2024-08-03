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
include(":data")
include(":domain")
include(":feature:settings")
include(":core:settings-data")
include(":common")
include(":core:requests-data")
