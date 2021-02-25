package com.prathap.rupeektask

import android.app.Activity
import android.app.Application
import com.prathap.rupeektask.di.AppComponent
import com.prathap.rupeektask.di.AppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * Application class to inject app to Dagger
 */
class WeatherApp : Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = AppInjector.init(this)
    }

    override fun activityInjector() = dispatchingAndroidInjector
}