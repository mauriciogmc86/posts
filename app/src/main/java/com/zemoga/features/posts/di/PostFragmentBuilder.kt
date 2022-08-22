package com.zemoga.features.posts.di

import androidx.lifecycle.ViewModel
import com.zemoga.di.scope.FragmentScope
import com.zemoga.di.scope.ViewModelKey
import com.zemoga.features.posts.data.repositories.PostsRepository
import com.zemoga.features.posts.ui.PostFragment
import com.zemoga.features.posts.ui.PostViewModel
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class PostFragmentBuilder {

    @FragmentScope
    @ContributesAndroidInjector(modules = [PostProvider::class])
    abstract fun bindPostFragment(): PostFragment
}

@Module
object PostProvider {

    @IntoMap
    @Provides
    @ViewModelKey(PostViewModel::class)
    fun provideViewModel(
        repository: PostsRepository
    ): ViewModel {
        return PostViewModel(repository)
    }
}
