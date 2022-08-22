package com.zemoga.features.postDetail.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.airbnb.epoxy.AsyncEpoxyController
import com.zemoga.R
import com.zemoga.databinding.ActivityPostDetailBinding
import com.zemoga.features.posts.data.models.PostCommentModel
import com.zemoga.features.posts.data.models.PostUserModel
import com.zemoga.features.posts.ui.widgets.commentItemView
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class PostDetailActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: PostDetailViewModel

    private lateinit var binding: ActivityPostDetailBinding
    private var menu: Menu? = null

    private val commentAdapter: CommentsAdapter by lazy { CommentsAdapter() }

    override fun androidInjector() = androidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
        setupViewModel()
        setupCommentsRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menu = menu
        menuInflater.inflate(R.menu.post_detail_menu, menu)
        changeMenuIcon(viewModel.post.isFavorite)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.favorite) {
            viewModel.setAsFavorite()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun changeMenuIcon(isFavorite: Boolean) {
        val icon = if (isFavorite) R.drawable.ic_star else R.drawable.ic_outline_star
        this.menu?.findItem(R.id.favorite)?.icon = resources.getDrawable(icon, null)
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)[PostDetailViewModel::class.java]
        lifecycle.addObserver(viewModel)
        viewModel.loading.observe(this) { loading ->
            binding.loadingView.visibility = if (loading) View.VISIBLE else View.GONE
        }
        viewModel.favorite.observe(this) { isFavorite -> changeMenuIcon(isFavorite) }
        viewModel.success.observe(this) { data ->
            bindUserData(data?.user)
            binding.textViewDescriptionValue.text = viewModel.post.body
            if (data?.comments.isNullOrEmpty()) {
                binding.recyclerView.visibility = View.GONE
                binding.textViewCommentsTitle.visibility = View.GONE
            } else {
                commentAdapter.dispatch(data?.comments ?: emptyList())
                binding.recyclerView.visibility = View.VISIBLE
                binding.textViewCommentsTitle.visibility = View.VISIBLE

            }
        }
    }

    private fun setupCommentsRecyclerView() {
        binding.recyclerView.setHasFixedSize(false)
        binding.recyclerView.setController(commentAdapter)
    }

    private fun bindUserData(user: PostUserModel?) = user?.let {
        binding.userInfoName.setData(getString(R.string.name_title), it.name)
        binding.userInfoEmail.setData(getString(R.string.email_title), it.email)
        binding.userInfoPhone.setData(getString(R.string.phone_title), it.phone)
        binding.userInfoWebsite.setData(getString(R.string.website_title), it.website)
    }

    internal class CommentsAdapter() : AsyncEpoxyController() {

        var data: List<PostCommentModel> = listOf()
            set(value) {
                field = value
                requestModelBuild()
            }

        override fun buildModels() {
            data.forEach { comment ->
                commentItemView {
                    id(comment.id)
                    data(comment)
                }
            }
        }

        fun dispatch(data: List<PostCommentModel>) {
            this.data = data
        }
    }
}

const val POST_ARG = "post_arg"