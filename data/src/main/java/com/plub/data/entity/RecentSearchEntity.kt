package com.plub.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = EntityTable.RECENT_SEARCH)
data class RecentSearchEntity(
    @PrimaryKey
    @ColumnInfo(name = "search") val search: String,
    @ColumnInfo(name = "saveTime") val saveTime: Long
)