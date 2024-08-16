package com.yas.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {

    commonExtension.apply {
        buildFeatures {
            compose = true
        }
    }
    dependencies {
        implementation(platform(libs.compose.bom))
        androidTestImplementation(platform(libs.compose.bom))
        debugImplementation(libs.ui.tooling)
        implementation(libs.ui.tooling.preview)
        implementation(libs.material3)
    }
}