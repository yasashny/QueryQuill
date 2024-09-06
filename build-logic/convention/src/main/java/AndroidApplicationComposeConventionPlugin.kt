import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.queryquill.app.configureCompose
import org.queryquill.app.libs


class AndroidApplicationComposeConventionPlugin : Plugin<Project> {


    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.plugins.compose.compiler.get().pluginId)
            }
            extensions.configure<ApplicationExtension> {
                configureCompose(this)
            }
        }
    }
}