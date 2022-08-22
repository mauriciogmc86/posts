package com.zemoga.features.favorites.di

import androidx.lifecycle.ViewModel
import com.zemoga.di.scope.FragmentScope
import com.zemoga.di.scope.ViewModelKey
import com.zemoga.features.favorites.ui.FavoritesFragment
import com.zemoga.features.favorites.ui.FavoritesViewModel
import com.zemoga.features.posts.data.repositories.PostsRepository
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class FavoritesFragmentBuilder {

    @FragmentScope
    @ContributesAndroidInjector(modules = [FavoritesProvider::class])
    abstract fun bindFavoritesFragment(): FavoritesFragment
}

@Module
object FavoritesProvider {

    @IntoMap
    @Provides
    @ViewModelKey(FavoritesViewModel::class)
    fun provideViewModel(
        repository: PostsRepository
    ): ViewModel {
        return FavoritesViewModel(repository)
    }
}