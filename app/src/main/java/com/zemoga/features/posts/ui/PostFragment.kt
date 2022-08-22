package com.zemoga.features.posts.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.airbnb.epoxy.EpoxyRecyclerView
import com.zemoga.databinding.FragmentPostsBinding
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class PostFragment : PostsParentFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: PostViewModel

    private var _binding: FragmentPostsBinding? = null
    private val binding get() = _binding!!

    override val recyclerView: EpoxyRecyclerView
        get() = binding.recyclerView

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupRecyclerView()
        binding.floatingButtonDelete.setOnClickListener {
            binding.buttonDeleteAll.visibility = View.VISIBLE
            binding.floatingButtonDelete.visibility = View.GONE
        }
        binding.buttonDeleteAll.setOnClickListener {
            binding.buttonDeleteAll.visibility = View.GONE
            binding.floatingButtonDelete.visibility = View.VISIBLE
            viewModel.deleteAll()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun refresh() {
        viewModel.gettingPosts()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)[PostViewModel::class.java]
        lifecycle.addObserver(viewModel)
        viewModel.loading.observe(viewLifecycleOwner) { loading ->
            binding.loadingView.visibility = if (loading) View.VISIBLE else View.GONE
        }
        viewModel.success.observe(viewLifecycleOwner) { data ->
            if (data != null) {
                postAdapter.dispatch(data)
            }
        }
    }

    companion object {
        fun newInstance(): PostFragment {
            return PostFragment()
        }
    }
}