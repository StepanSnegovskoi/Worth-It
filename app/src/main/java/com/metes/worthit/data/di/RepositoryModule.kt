package com.metes.worthit.data.di

import com.metes.worthit.data.local.repository.InternalStorageImpl
import com.metes.worthit.data.local.repository.ItemsRepositoryImpl
import com.metes.worthit.domain.repository.ItemsRepository
import com.metes.worthit.domain.repository.LocalMediaRepository
import com.metes.worthit.domain.repository.StorageRepository
import com.metes.worthit.domain.utils.Result
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindItemsRepository(
        itemsRepository: ItemsRepositoryImpl
    ): ItemsRepository

    @Singleton
    @Binds
    abstract fun bindInternalRepository(
        internalRepository: InternalStorageImpl
    ): LocalMediaRepository
}