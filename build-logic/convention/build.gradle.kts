import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    implementation(libs.agp)
    implementation(libs.kotlin.gradlePlugin)
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
    implementation(libs.room.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("androidRoom") {
            id = "queryquill.android.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
        register("androidApplication") {
            id = "queryquill.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = "queryquill.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "queryquill.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "queryquill.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
    }
}