package com.metes.worthit.core.testing

import com.metes.worthit.core.domain.utils.DateProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDate

class FakeCurrentDateProvider : DateProvider {
    override val currentDateFlow: Flow<LocalDate> = flowOf(LocalDate.now())
}
