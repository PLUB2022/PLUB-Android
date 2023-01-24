package com.plub.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.plub.data.entity.EntityTable
import com.plub.data.entity.RecentSearchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentSearchDao {
    @Query("SELECT * FROM ${EntityTable.RECENT_SEARCH} ORDER BY saveTime DESC LIMIT :count")
    fun getSearches(count: Int): Flow<List<RecentSearchEntity>>

    @Query("SELECT COUNT(*) FROM ${EntityTable.RECENT_SEARCH}")
    suspend fun getSearchesCount(): Int

    @Query("DELETE FROM ${EntityTable.RECENT_SEARCH} WHERE search = :search")
    suspend fun deleteBySearch(search: String)

    @Insert(onConflict = REPLACE)
    suspend fun insert(recentSearchEntity: RecentSearchEntity)

    @Query("DELETE FROM ${EntityTable.RECENT_SEARCH} WHERE saveTime IN (SELECT saveTime FROM ${EntityTable.RECENT_SEARCH} ORDER BY saveTime ASC LIMIT :count )")
    suspend fun deleteOldestSearch(count: Int)

    @Query("DELETE FROM ${EntityTable.RECENT_SEARCH}")
    suspend fun deleteAll()
}