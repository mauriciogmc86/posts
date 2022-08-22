package com.zemoga.features.posts.ui

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
import javax.inject.Inject

class PostViewModel @Inject constructor(
    private val repository: PostsRepository
) : ViewModel(), DefaultLifecycleObserver {

    private var postJob: Job? = null
    private var deleteJob: Job? = null

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _success = MutableLiveData<List<PostModel>?>(emptyList())
    val success: LiveData<List<PostModel>?> = _success

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        gettingPosts()
    }

    override fun onCleared() {
        super.onCleared()
        postJob?.cancel()
        postJob = null
        deleteJob?.cancel()
        deleteJob = null
    }

    fun deleteAll() {
        _loading.value = true
        deleteJob = CoroutineScope(Dispatchers.IO).launch {
            repository.deleteAll()
            withContext(Dispatchers.Main) {
                _success.value = _success.value?.filter { it.isFavorite }
                _loading.value = false
            }
        }
    }

    fun gettingPosts() {
        _loading.value = true
        postJob = CoroutineScope(Dispatchers.IO).launch {
            val items = repository.getPosts()
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
