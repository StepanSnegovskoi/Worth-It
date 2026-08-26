package com.metes.worthit.app.di

import com.metes.worthit.core.common.ApplicationScope
import com.metes.worthit.core.common.DefaultDispatcher
import com.metes.worthit.core.common.IoDispatcher
import com.metes.worthit.core.common.MainDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import java.time.Clock

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @ApplicationScope
    fun provideApplicationScope(): CoroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.Default)

    @Provides
    @MainDispatcher
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @Provides
    @DefaultDispatcher
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @IoDispatcher
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    fun provideClock(): Clock {
        return Clock.systemDefaultZone()
    }
}
