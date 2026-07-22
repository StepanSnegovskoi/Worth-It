package com.metes.worthit.core.database.di

import android.content.Context
import androidx.room.Room
import com.metes.worthit.core.database.db.ItemsDao
import com.metes.worthit.core.database.db.WorthItDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideItemsDatabase(
        @ApplicationContext context: Context
    ): WorthItDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = WorthItDatabase::class.java,
            name = WorthItDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration(true).build()
    }

    @Singleton
    @Provides
    fun provideItemsDao(
        worthItDatabase: WorthItDatabase
    ): ItemsDao {
        return worthItDatabase.dao()
    }
}