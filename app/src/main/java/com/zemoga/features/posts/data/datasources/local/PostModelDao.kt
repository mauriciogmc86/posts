package com.zemoga.features.posts.data.datasources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PostModelDao {

    @Query("DELETE FROM post_entity WHERE is_favorite = 0")
    suspend fun deleteAll()

    @Query("SELECT * FROM post_entity WHERE is_favorite = 1")
    suspend fun getFavorites(): List<PostModelEntity>

    @Query("SELECT * FROM post_entity WHERE id = :id")
    suspend fun getPostById(id: Int): PostModelEntity?

    @Query("UPDATE post_entity SET is_favorite = :isFavorite WHERE id = :id")
    suspend fun setFavorite(id: Int, isFavorite: Boolean)

    @Query("UPDATE post_entity SET is_read = :isRead WHERE id = :id")
    suspend fun setRead(id: Int, isRead: Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(post: PostModelEntity)
}
