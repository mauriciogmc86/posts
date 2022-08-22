package com.zemoga.di.component

import android.app.Application
import com.zemoga.ZemogaApp
import com.zemoga.di.modules.ActivityBuilder
import com.zemoga.di.modules.DatabaseModule
import com.zemoga.di.modules.FactoryModule
import com.zemoga.di.modules.NetworkModule
import com.zemoga.features.MainActivity
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        FactoryModule::class,
        NetworkModule::class,
        DatabaseModule::class,
        ActivityBuilder::class,
        AndroidSupportInjectionModule::class
    ]
)

interface AppComponent : AndroidInjector<ZemogaApp> {

    fun inject(app: MainActivity)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun application(application: Application): Builder
    }
}
