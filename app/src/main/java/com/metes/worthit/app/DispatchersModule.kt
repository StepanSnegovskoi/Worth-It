package com.metes.worthit.app

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DispatchersModule {

    @Singleton
    @Binds
    abstract fun bindDispatcherProvider(
        dispatchers: StandardDispatchers
    ): DispatcherProvider
}