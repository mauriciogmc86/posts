package com.zemoga.features.posts.data.datasources.remote

import com.zemoga.features.posts.data.models.PostCommentModel
import com.zemoga.features.posts.data.models.PostModel
import com.zemoga.features.posts.data.models.PostUserModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PostsApi {

    @GET("posts")
    suspend fun getPosts(): Response<List<PostModel>>

    @GET("posts/{id}/comments")
    suspend fun getComments(@Path("id") id: Int): Response<List<PostCommentModel>>

    @GET("users/{id}")
    suspend fun getPostUser(@Path("id") id: Int): Response<PostUserModel>
}
