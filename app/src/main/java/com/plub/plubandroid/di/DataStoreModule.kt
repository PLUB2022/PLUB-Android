package com.plub.plubandroid.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.Serializer
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.plub.data.repository.PlubJwtRepositoryImpl
import com.plub.data.repository.PrefDataStoreRepositoryImpl
import com.plub.data.util.Crypto
import com.plub.data.util.CryptoImpl
import com.plub.data.util.PlubJwtToken
import com.plub.data.util.PlubJwtTokenSerializer
import com.plub.domain.repository.PlubJwtRepository
import com.plub.domain.repository.PrefDataStoreRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataStoreModule {

    @Binds
    @Singleton
    abstract fun bindCrypto(crypto: CryptoImpl): Crypto

    @Binds
    @Singleton
    abstract fun bindPlubJwtTokenSerializer(plubJwtTokenSerializer: PlubJwtTokenSerializer): Serializer<PlubJwtToken>

    @Binds
    @Singleton
    abstract fun bindPlubJwtTokenRepository(
        plubJwtRepositoryImpl: PlubJwtRepositoryImpl
    ) : PlubJwtRepository

    @Binds
    @Singleton
    abstract fun bindPrefDataStoreRepository(
        prefDataStoreRepositoryImpl: PrefDataStoreRepositoryImpl
    ) : PrefDataStoreRepository

    companion object {
        @Provides
        @Singleton
        fun providePreferenceDataStore(
            @ApplicationContext applicationContext: Context
        ): DataStore<Preferences> = PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            migrations = listOf(SharedPreferencesMigration(applicationContext, "plub_preferences")),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { applicationContext.preferencesDataStoreFile("plub_preferences") }
        )

        @Provides
        @Singleton
        fun provideEncryptedDataStore(
            @ApplicationContext applicationContext: Context,
            serializer: Serializer<PlubJwtToken>
        ): DataStore<PlubJwtToken> = DataStoreFactory.create(
            serializer = serializer,
            produceFile = { applicationContext.dataStoreFile("plub_token") }
        )
    }
}