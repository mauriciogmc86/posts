package com.zemoga.features.posts.ui.adapters

import com.airbnb.epoxy.AsyncEpoxyController
import com.zemoga.features.posts.data.models.PostModel
import com.zemoga.features.posts.ui.widgets.PostItemView
import com.zemoga.features.posts.ui.widgets.postItemView

class PostAdapter constructor(
    private val listener: PostItemView.Listener
) : AsyncEpoxyController() {

    var data: List<PostModel> = listOf()
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        data.forEach { post ->
            postItemView {
                id(post.id)
                data(post)
                listener(this@PostAdapter.listener)
            }
        }
    }

    fun dispatch(data: List<PostModel>) {
        this.data = data
    }
}
