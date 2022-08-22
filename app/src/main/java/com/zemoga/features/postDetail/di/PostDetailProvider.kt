package com.zemoga.features.postDetail.di

import androidx.lifecycle.ViewModel
import com.zemoga.di.scope.ViewModelKey
import com.zemoga.features.postDetail.ui.POST_ARG
import com.zemoga.features.postDetail.ui.PostDetailActivity
import com.zemoga.features.postDetail.ui.PostDetailViewModel
import com.zemoga.features.posts.data.models.PostModel
import com.zemoga.features.posts.data.repositories.PostsRepository
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class PostDetailProvider {
    @IntoMap
    @Provides
    @ViewModelKey(PostDetailViewModel::class)
    fun provideViewModel(activity: PostDetailActivity, repository: PostsRepository): ViewModel {
        val args = activity.intent.getParcelableExtra<PostModel>(POST_ARG)

        return PostDetailViewModel(args!!, repository)
    }
}