import androidx.room3.gradle.RoomExtension
import com.metes.worthit.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidRoom3ConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "androidx.room3")
            apply(plugin = "com.google.devtools.ksp")

            extensions.configure<RoomExtension> {
                schemaDirectory("$projectDir/schemas")
            }

            dependencies {
                "ksp"(libs.findLibrary("room3.compiler").get())
                "implementation"(libs.findLibrary("room3.runtime").get())
                "androidTestImplementation"(libs.findLibrary("room3.testing").get())
            }
        }
    }
}
