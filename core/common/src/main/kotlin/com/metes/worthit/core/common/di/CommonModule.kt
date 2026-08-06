package com.metes.worthit.core.common.di

import com.metes.worthit.core.common.DispatcherProvider
import com.metes.worthit.core.common.StandardDispatchers
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CommonModule {

    @Singleton
    @Binds
    abstract fun bindDispatcherProvider(
        dispatchers: StandardDispatchers
    ): DispatcherProvider
}