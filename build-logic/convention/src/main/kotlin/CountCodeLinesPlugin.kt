import com.metes.worthit.convention.CountCodeLinesExtension
import com.metes.worthit.convention.CountCodeLinesTask
import com.metes.worthit.convention.SortType
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.assign

class CountCodeLinesPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val codeLinesExtension =
                extensions.create<CountCodeLinesExtension>("countCodeLines").apply {
                    sourceFiles = rootProject.layout.projectDirectory
                    outputFile = project.layout.buildDirectory.file("code_lines.md")
                    sortType = SortType.DESCENDING_ROWS
                }

            tasks.register<CountCodeLinesTask>("countCodeLines") {
                sourceFiles.from(codeLinesExtension.sourceFiles)
                sourceFiles.include(listOf("**/*.kt"))
                sourceFiles.exclude(listOf("**/build/**", "**/test/**", "**/androidTest/**"))

                outputFile = codeLinesExtension.outputFile
                sortType = codeLinesExtension.sortType
            }
        }
    }
}
