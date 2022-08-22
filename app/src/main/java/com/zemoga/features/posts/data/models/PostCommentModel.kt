package com.zemoga.features.posts.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PostCommentModel(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("body") val body: String,
    @SerializedName("email") val email: String
) : Parcelable
