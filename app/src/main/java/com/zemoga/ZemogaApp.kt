package com.zemoga

import android.app.Application
import com.zemoga.di.component.AppComponent
import com.zemoga.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class ZemogaApp : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    private var _component: AppComponent? = null

    override fun onCreate() {
        super.onCreate()
        initializeInjector()
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return androidInjector
    }

    override fun onTerminate() {
        super.onTerminate()
        _component = null
    }

    private fun initializeInjector() {
        _component = DaggerAppComponent.builder()
            .application(this)
            .build()
            .apply { inject(this@ZemogaApp) }
    }
}