package com.zemoga.di.modules

import com.zemoga.di.scope.ActivityScope
import com.zemoga.features.MainActivity
import com.zemoga.features.favorites.di.FavoritesFragmentBuilder
import com.zemoga.features.postDetail.di.PostDetailProvider
import com.zemoga.features.postDetail.ui.PostDetailActivity
import com.zemoga.features.posts.di.PostFragmentBuilder
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector(
        modules = [
            PostFragmentBuilder::class,
            FavoritesFragmentBuilder::class,
        ]
    )
    @ActivityScope
    abstract fun bindMainActivity(): MainActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [PostDetailProvider::class])
    abstract fun bindPostDetailActivity(): PostDetailActivity
}