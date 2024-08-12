import com.android.build.gradle.LibraryExtension
import com.yas.convention.configureCompose
import com.yas.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryComposeConventionPlugin : Plugin<Project> {


    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.plugins.compose.compiler.get().pluginId)
            }
            extensions.configure<LibraryExtension> {
                configureCompose(this)
            }
        }
    }
}