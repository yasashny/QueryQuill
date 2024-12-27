pluginManagement {
    includeBuild("build-logic")
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
gradle.startParameter.excludedTaskNames.addAll(listOf(":build-logic:convention:testClasses"))

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
rootProject.name = "QueryQuill"
include(":app")
include(":feature:settings")
include(":core:ui")
include(":core:model")
include(":feature:response")
include(":core:utils")
include(":feature:request")
include(":feature:new-transaction")
include(":feature:request-code-editor")
include(":feature:transaction")
include(":core:domain")
include(":core:common")
include(":core:designsystem")
include(":feature:cookie")
include(":core:database")
include(":core:datastore")
include(":core:network")
include(":core:data")
