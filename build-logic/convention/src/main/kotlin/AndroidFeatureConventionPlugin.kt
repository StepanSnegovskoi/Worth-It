import com.metes.worthit.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "worthit.android.library")
            apply(plugin = "worthit.hilt")

            dependencies {
                "implementation"(project(":core:ui"))
                "implementation"(project(":core:domain"))
                "implementation"(project(":core:common"))
                "implementation"(project(":core:designsystem"))
                "implementation"(project(":core:presentation"))
                "implementation"(libs.findLibrary("androidx.hilt.lifecycle.viewmodelCompose").get())
            }
        }
    }
}
