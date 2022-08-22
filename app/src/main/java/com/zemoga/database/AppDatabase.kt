package com.zemoga.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zemoga.features.posts.data.datasources.local.PostModelDao
import com.zemoga.features.posts.data.datasources.local.PostModelEntity

@Database(entities = [PostModelEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun providePostModelDao(): PostModelDao
}
