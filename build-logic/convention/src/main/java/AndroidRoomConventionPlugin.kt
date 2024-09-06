import androidx.room.gradle.RoomExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.queryquill.app.annotationProcessor
import org.queryquill.app.implementation
import org.queryquill.app.ksp
import org.queryquill.app.libs

class AndroidRoomConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply(libs.plugins.google.devtools.ksp.get().pluginId)
            pluginManager.apply(libs.plugins.androidx.room.get().pluginId)


            extensions.configure<RoomExtension> {
                schemaDirectory("$projectDir/schemas")
            }

            dependencies {
                implementation(libs.room.runtime)
                annotationProcessor(libs.room.compiler)
                ksp(libs.room.compiler)
                implementation(libs.room.ktx)
            }
        }
    }
}