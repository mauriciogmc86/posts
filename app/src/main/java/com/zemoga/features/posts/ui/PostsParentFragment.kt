package com.zemoga.features.posts.ui

import android.content.Intent
import androidx.fragment.app.Fragment
import com.airbnb.epoxy.EpoxyRecyclerView
import com.zemoga.features.postDetail.ui.POST_ARG
import com.zemoga.features.postDetail.ui.PostDetailActivity
import com.zemoga.features.posts.data.models.PostModel
import com.zemoga.features.posts.ui.adapters.PostAdapter
import com.zemoga.features.posts.ui.widgets.PostItemView

abstract class PostsParentFragment : Fragment(), PostItemView.Listener {

    val postAdapter: PostAdapter by lazy { PostAdapter(this) }

    abstract val recyclerView: EpoxyRecyclerView

    abstract fun refresh()

    override fun onPostClicked(data: PostModel) {
        Intent(requireActivity(), PostDetailActivity::class.java).apply {
            putExtra(POST_ARG, data)
            requireActivity().startActivity(this)
        }
    }

    fun setupRecyclerView() {
        recyclerView.setHasFixedSize(false)
        recyclerView.setController(postAdapter)
        postAdapter.dispatch(emptyList())
    }
}