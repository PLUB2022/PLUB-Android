package com.plub.plubandroid.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.Serializer
import androidx.datastore.dataStoreFile
import com.plub.data.repository.PlubJwtTokenRepositoryImpl
import com.plub.data.util.Crypto
import com.plub.data.util.CryptoImpl
import com.plub.data.util.PlubJwtToken
import com.plub.data.util.PlubJwtTokenSerializer
import com.plub.domain.repository.PlubJwtTokenRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
        plubJwtTokenRepositoryImpl: PlubJwtTokenRepositoryImpl
    ) : PlubJwtTokenRepository

    companion object {
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