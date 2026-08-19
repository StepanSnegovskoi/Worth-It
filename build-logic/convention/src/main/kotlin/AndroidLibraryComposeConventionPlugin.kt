import com.android.build.api.dsl.LibraryExtension
import com.metes.worthit.convention.configureAndroidCompose
import com.metes.worthit.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidLibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.android.library")
            apply(plugin = "org.jetbrains.kotlin.plugin.compose")

            val extension = extensions.getByType<LibraryExtension>()
            configureAndroidCompose(extension)

            dependencies {
                "implementation"(libs.findLibrary("androidx-lifecycle-runtime-compose").get())
            }
        }
    }
}
