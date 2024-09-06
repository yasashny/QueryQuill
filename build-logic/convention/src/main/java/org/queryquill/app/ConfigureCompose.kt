package org.queryquill.app

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension

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
    composeCompiler {
        enableStrongSkippingMode = true
        metricsDestination = layout.buildDirectory.dir("compose_compiler")
        reportsDestination = layout.buildDirectory.dir("compose_compiler")
    }

}

fun Project.composeCompiler(configure: Action<ComposeCompilerGradlePluginExtension>): Unit =
    (this as org.gradle.api.plugins.ExtensionAware).extensions.configure(
        "composeCompiler", configure
    )