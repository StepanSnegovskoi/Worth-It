package com.metes.worthit.core.domain.utils

import java.time.LocalDate
import kotlinx.coroutines.flow.Flow

interface DateProvider {
    val currentDateFlow: Flow<LocalDate>
}
