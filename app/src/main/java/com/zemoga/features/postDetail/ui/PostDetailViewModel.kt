package com.zemoga.features.postDetail.ui

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zemoga.features.posts.data.models.PostDetailModel
import com.zemoga.features.posts.data.models.PostModel
import com.zemoga.features.posts.data.repositories.PostsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PostDetailViewModel constructor(
    private val args: PostModel,
    private val repository: PostsRepository
) : ViewModel(), DefaultLifecycleObserver {

    private var job: Job? = null

    var post = args

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _favorite = MutableLiveData<Boolean>()
    val favorite: LiveData<Boolean> = _favorite

    private val _success = MutableLiveData<PostDetailModel?>(null)
    val success: LiveData<PostDetailModel?> = _success

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        setAsRead()
        gettingPosts()
        _favorite.value = post.isFavorite
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
        job = null
    }

    fun setAsFavorite() {
        CoroutineScope(Dispatchers.IO).launch {
            post = post.copy(isFavorite = !post.isFavorite)
            repository.setFavorite(args.id, post.isFavorite)
            withContext(Dispatchers.Main) {
                _favorite.value = post.isFavorite
            }
        }
    }

    private fun setAsRead() {
        CoroutineScope(Dispatchers.IO).launch {
            repository.setRead(args.id, isRead = true)
        }
    }

    private fun gettingPosts() {
        _loading.value = true
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getPostCommentsById(args.id, args.userId)
            withContext(Dispatchers.Main) {
                _success.value = response
                _loading.value = false
            }
        }
    }
}