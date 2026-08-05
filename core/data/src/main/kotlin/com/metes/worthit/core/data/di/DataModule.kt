package com.metes.worthit.core.data.di

import com.metes.worthit.core.data.repository.InternalStorageImpl
import com.metes.worthit.core.data.repository.ItemsRepositoryImpl
import com.metes.worthit.core.data.utils.CurrentDateProvider
import com.metes.worthit.core.data.utils.StandardDispatchers
import com.metes.worthit.core.domain.repository.ItemsRepository
import com.metes.worthit.core.domain.repository.StorageRepository
import com.metes.worthit.core.domain.utils.DateProvider
import com.metes.worthit.core.domain.utils.DispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Singleton
    @Binds
    abstract fun bindDispatcherProvider(
        dispatchers: StandardDispatchers
    ): DispatcherProvider

    @Singleton
    @Binds
    abstract fun bindItemsRepository(
        itemsRepository: ItemsRepositoryImpl
    ): ItemsRepository

    @Singleton
    @Binds
    abstract fun bindInternalRepository(
        internalRepository: InternalStorageImpl
    ): StorageRepository

    @Singleton
    @Binds
    abstract fun bindDateProvider(
        currentDateProvider: CurrentDateProvider
    ): DateProvider
}
