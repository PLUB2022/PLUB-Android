package com.plub.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.plub.data.entity.EntityTable
import com.plub.data.entity.RecentSearchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentSearchDao {
    @Query("SELECT * FROM ${EntityTable.RECENT_SEARCH} ORDER BY id DESC LIMIT :count")
    fun getSearches(count: Int): Flow<List<RecentSearchEntity>>

    @Query("DELETE FROM ${EntityTable.RECENT_SEARCH} WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Insert
    suspend fun insert(recentSearchEntity: RecentSearchEntity)

    @Query("DELETE FROM ${EntityTable.RECENT_SEARCH} WHERE id IN (SELECT id FROM ${EntityTable.RECENT_SEARCH} ORDER BY id ASC LIMIT :count )")
    suspend fun deleteOldestSearch(count: Int)

    @Query("DELETE FROM ${EntityTable.RECENT_SEARCH}")
    suspend fun deleteAll()
}