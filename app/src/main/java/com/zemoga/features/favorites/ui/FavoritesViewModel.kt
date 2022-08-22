package com.zemoga.features.favorites.ui

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zemoga.features.posts.data.models.PostModel
import com.zemoga.features.posts.data.repositories.PostsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoritesViewModel constructor(
    private val repository: PostsRepository
) : ViewModel(), DefaultLifecycleObserver {

    private var favoritesJob: Job? = null

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _success = MutableLiveData<List<PostModel>?>(emptyList())
    val success: LiveData<List<PostModel>?> = _success

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        gettingFavoritePosts()
    }

    override fun onCleared() {
        super.onCleared()
        favoritesJob?.cancel()
        favoritesJob = null
    }

    fun gettingFavoritePosts() {
        _loading.value = true
        favoritesJob = CoroutineScope(Dispatchers.IO).launch {
            val items = repository.getFavorites()
            withContext(Dispatchers.Main) {
                if (items.isNotEmpty()) {
                    _success.value = items
                } else {
                    _success.value = null
                }
                _loading.value = false
            }
        }
    }
}