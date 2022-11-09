package com.plub.domain.repository

interface PrefDataStoreRepository {
    suspend fun getString(key: String): Result<String>

    suspend fun getInt(key: String): Result<Int>

    suspend fun getBoolean(key: String): Result<Boolean?>

    suspend fun setString(key: String, value: String)

    suspend fun setInt(key: String, value: Int)

    suspend fun setBoolean(key: String, value: Boolean)
}