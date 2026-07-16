package com.metes.worthit.app

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.metes.worthit.data.di.ApplicationScope
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AppModule {

    @Singleton
    @Binds
    abstract fun bindDispatcherProvider(
        dispatchers: StandardDispatchers
    ): DispatcherProvider

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

        @Singleton
        @Provides
        @ApplicationScope
        fun provideApplicationScope(
            dispatchers: StandardDispatchers
        ): CoroutineScope = CoroutineScope(SupervisorJob() + dispatchers.default)
    }
}