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
    @Query("SELECT * FROM ${EntityTable.RECENT_SEARCH} ORDER BY saveTime DESC LIMIT :arg0")
    fun getSearches(count: Int): Flow<List<RecentSearchEntity>>

    @Query("SELECT COUNT(*) FROM ${EntityTable.RECENT_SEARCH}")
    fun getSearchesCount(): Int

    @Query("DELETE FROM ${EntityTable.RECENT_SEARCH} WHERE search = :arg0")
    fun deleteBySearch(search: String): Int

    @Insert(onConflict = REPLACE)
    fun insert(recentSearchEntity: RecentSearchEntity)

    @Query("DELETE FROM ${EntityTable.RECENT_SEARCH} WHERE saveTime IN (SELECT saveTime FROM ${EntityTable.RECENT_SEARCH} ORDER BY saveTime ASC LIMIT :arg0 )")
    fun deleteOldestSearch(count: Int): Int

    @Query("DELETE FROM ${EntityTable.RECENT_SEARCH}")
    fun deleteAll()
}