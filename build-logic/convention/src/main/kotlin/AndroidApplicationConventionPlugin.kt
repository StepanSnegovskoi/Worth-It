
import com.android.build.api.dsl.ApplicationExtension
import com.metes.worthit.convention.CountCodeLinesTask
import com.metes.worthit.convention.configureKotlinAndroid
import com.metes.worthit.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.register

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.android.application")

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
            }

            tasks.register<CountCodeLinesTask>("countCodeLinesTask") {
                sourceFiles.from(rootProject.layout.projectDirectory)

                sourceFiles.include("**/*.kt")
                sourceFiles.exclude("**/build/**")
                sourceFiles.exclude("**/androidTest/**")
                sourceFiles.exclude("**/test/**")
            }

            tasks.named("check") {
                dependsOn("countCodeLinesTask")
            }
        }
    }
}
