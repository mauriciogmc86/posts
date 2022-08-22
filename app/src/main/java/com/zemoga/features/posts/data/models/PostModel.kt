package com.zemoga.features.posts.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostModel(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("body") val body: String,
    @SerializedName("userId") val userId: Int,
    @Transient val isRead: Boolean = false,
    @Transient val isFavorite: Boolean = false,
) : Parcelable
