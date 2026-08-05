package com.metes.worthit.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.metes.worthit.core.datastore.DefaultUserSettings
import com.metes.worthit.core.domain.utils.DateProvider
import com.metes.worthit.core.domain.utils.UserSettings
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DatastoreModule {

    @Singleton
    @Binds
    abstract fun bindUserSettings(
        currentDateProvider: DefaultUserSettings
    ): UserSettings

    companion object {
        @Singleton
        @Provides
        fun provideDatastore(
            @ApplicationContext context: Context
        ): DataStore<Preferences> {
            return PreferenceDataStoreFactory.create {
                context.preferencesDataStoreFile("user_settings")
            }
        }
    }
}