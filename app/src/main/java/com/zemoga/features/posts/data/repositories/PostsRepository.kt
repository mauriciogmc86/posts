package com.zemoga.features.posts.data.repositories

import com.zemoga.features.posts.data.datasources.local.PostModelDao
import com.zemoga.features.posts.data.datasources.local.PostModelEntity
import com.zemoga.features.posts.data.datasources.remote.PostsApi
import com.zemoga.features.posts.data.models.PostDetailModel
import com.zemoga.features.posts.data.models.PostModel
import javax.inject.Inject

class PostsRepository @Inject constructor(
    private val dao: PostModelDao,
    private val postsApi: PostsApi
) {

    suspend fun getPosts(): List<PostModel> {
        return try {
            var response = postsApi.getPosts().body()
            response?.let { posts ->
                response = posts.map { post ->
                    val exists = dao.getPostById(post.id)
                    if (exists != null) {
                        post.copy(isRead = exists.isRead, isFavorite = exists.isFavorite)
                    } else {
                        dao.insert(
                            PostModelEntity(
                                post.id,
                                post.title,
                                post.body,
                                isRead = false,
                                isFavorite = false,
                                userId = post.userId
                            )
                        )
                        post
                    }
                }
            }
            response ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getFavorites(): List<PostModel> {
        return dao.getFavorites().map {
            PostModel(it.id, it.title, it.body, it.userId, it.isRead, it.isFavorite)
        }
    }

    suspend fun getPostCommentsById(postId: Int, userId: Int): PostDetailModel? {
        return try {
            val user = postsApi.getPostUser(userId)
            val comments = postsApi.getComments(postId)
            PostDetailModel(user.body(), comments.body())
        } catch (e: Exception) {
            null
        }
    }

    suspend fun setRead(id: Int, isRead: Boolean) {
        dao.setRead(id, isRead)
    }

    suspend fun setFavorite(id: Int, isFavorite: Boolean) {
        dao.setFavorite(id, isFavorite)
    }

    suspend fun deleteAll() {
        dao.deleteAll()
    }
}