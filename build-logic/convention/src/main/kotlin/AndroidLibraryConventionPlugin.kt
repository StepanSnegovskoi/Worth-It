import com.android.build.api.dsl.LibraryExtension
import com.metes.worthit.convention.configureKotlinAndroid
import com.metes.worthit.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.android.library")

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
            }

            dependencies {
                "implementation"(libs.findLibrary("androidx-core-ktx").get())
                "testImplementation"(libs.findLibrary("kotlin-test").get())
                "testImplementation"(libs.findLibrary("junit").get())
                "androidTestImplementation"(libs.findLibrary("kotlin-test").get())
            }
        }
    }
}
