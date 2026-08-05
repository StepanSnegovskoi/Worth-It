package com.metes.worthit.core.domain.utils

import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface DateProvider {
    val currentDateFlow: Flow<LocalDate>
}
