package com.plub.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.plub.data.entity.EntityTable
import com.plub.data.entity.RecentSearchEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentSearchDao {
    @Query("SELECT * FROM ${EntityTable.RECENT_SEARCH}")
    fun getAll(): Flow<List<RecentSearchEntity>>

    @Query("DELETE FROM ${EntityTable.RECENT_SEARCH} WHERE id = :id")
    fun deleteById(id:Int)
}