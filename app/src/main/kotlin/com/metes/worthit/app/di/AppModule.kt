package com.metes.worthit.app.di

import com.metes.worthit.core.domain.utils.DispatcherProvider
import com.metes.worthit.core.common.ApplicationScope
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import java.time.Clock
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    @ApplicationScope
    fun provideApplicationScope(
        dispatchers: DispatcherProvider
    ): CoroutineScope = CoroutineScope(SupervisorJob() + dispatchers.default)

    @Provides
    fun provideClock(): Clock {
        return Clock.systemDefaultZone()
    }
}
