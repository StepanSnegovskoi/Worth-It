package com.metes.worthit.convention

import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileTree
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import java.io.File

enum class SortType {
    ASCENDING_ROWS,
    DESCENDING_ROWS;
}

@CacheableTask
internal abstract class CountCodeLinesTask : DefaultTask() {

    @get:Input
    abstract val sortType: Property<SortType>

    @get:InputFiles
    @get:PathSensitive(PathSensitivity.RELATIVE)
    abstract val sourceFiles: ConfigurableFileTree

    @get:OutputFile
    abstract val outputFile: RegularFileProperty

    init {
        outputFile.convention(project.layout.buildDirectory.file("code_lines.md"))
        sortType.convention(SortType.DESCENDING_ROWS)
    }

    @TaskAction
    fun countCodeLines() {
        val outputFile = outputFile.get().asFile

        writeHeader(outputFile)
        writeHeaderOfTable(outputFile)
        writeFilesInfoInTable(outputFile)
    }

    private fun writeHeader(file: File) {
        file.writeText("""
            # Count of Lines
        """.trimIndent().plus("\n"))
    }

    private fun writeHeaderOfTable(file: File) {
        file.appendText("""
            |№|Name|Lines|
            |-|----|-----|
        """.trimIndent().plus("\n"))
    }

    private fun writeFilesInfoInTable(file: File) {
        val sortType = sortType.get()

        val sortedFiles = sourceFiles.sortedBy {
            if (sortType == SortType.DESCENDING_ROWS) {
                -it.readLines().count()
            } else {
                it.readLines().count()
            }
        }

        var i = 1
        var total = 0
        val content = sortedFiles.joinToString("\n") { file ->
            val codeLines = file.readLines().count()
            total += codeLines
            "|${i++}|${file.name}|$codeLines|"
        }

        file.appendText("|Total|Total|$total|\n")
        file.appendText(content)
    }
}
