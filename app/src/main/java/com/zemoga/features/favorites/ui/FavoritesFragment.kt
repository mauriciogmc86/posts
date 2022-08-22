package com.zemoga.features.favorites.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.airbnb.epoxy.EpoxyRecyclerView
import com.zemoga.databinding.FragmentFavoritesBinding
import com.zemoga.features.posts.ui.PostsParentFragment
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class FavoritesFragment : PostsParentFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: FavoritesViewModel

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    override val recyclerView: EpoxyRecyclerView
        get() = binding.recyclerView

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupRecyclerView()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun refresh() {
        viewModel.gettingFavoritePosts()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory)[FavoritesViewModel::class.java]
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
        fun newInstance(): FavoritesFragment {
            return FavoritesFragment()
        }
    }
}