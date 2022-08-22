package com.zemoga.di.modules

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.zemoga.database.AppDatabase
import com.zemoga.features.posts.data.datasources.local.PostModelDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(application: Application): AppDatabase {
        val databaseName = "zemoga-posts-db"
        return Room.databaseBuilder(application, AppDatabase::class.java, databaseName)
            .fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun providePostModelDao(database: AppDatabase): PostModelDao {
        return database.providePostModelDao()
    }
}
