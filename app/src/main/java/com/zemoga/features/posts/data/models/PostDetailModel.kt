package com.zemoga.features.posts.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostDetailModel(
    @SerializedName("user") val user: PostUserModel? = null,
    @SerializedName("comments") val comments: List<PostCommentModel>? = null
) : Parcelable
