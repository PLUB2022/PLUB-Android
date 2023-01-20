package com.plub.plubandroid

import androidx.room.Database
import androidx.room.RoomDatabase
import com.plub.data.dao.RecentSearchDao
import com.plub.data.entity.RecentSearchEntity

@Database(entities = [RecentSearchEntity::class], version = 1)
abstract class RoomDataBase : RoomDatabase() {
    abstract fun recentSearchDao(): RecentSearchDao
}