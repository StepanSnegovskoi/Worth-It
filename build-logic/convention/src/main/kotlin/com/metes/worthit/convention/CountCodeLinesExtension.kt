package com.metes.worthit.convention

import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property

interface CountCodeLinesExtension {
    val sourceFiles: DirectoryProperty
    val outputFile: RegularFileProperty
    val sortType: Property<SortType>
}
