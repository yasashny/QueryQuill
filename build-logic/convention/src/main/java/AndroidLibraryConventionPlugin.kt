import com.android.build.gradle.LibraryExtension
import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.queryquill.app.configureKotlinAndroid
import org.queryquill.app.libs

class AndroidLibraryConventionPlugin : Plugin<Project> {


    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.plugins.android.library.get().pluginId)
                apply(libs.plugins.kotlin.android.get().pluginId)
            }
            extensions.configure<LibraryExtension> {
                defaultConfig.targetSdk = 35
                configureKotlinAndroid(this)
                kotlinOptions {
                    jvmTarget = "17"
                }
                defaultConfig.consumerProguardFiles("consumer-rules.pro")

            }
        }
    }
}

private fun LibraryExtension.kotlinOptions(configure: Action<org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions>): Unit =
    (this as org.gradle.api.plugins.ExtensionAware).extensions.configure("kotlinOptions", configure)

