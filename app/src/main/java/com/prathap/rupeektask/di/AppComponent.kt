package com.prathap.rupeektask.di

import android.app.Application
import com.prathap.rupeektask.WeatherApp
import com.prathap.rupeektask.di.modules.ActivityModule
import com.prathap.rupeektask.di.modules.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * Bridge b/w Provides (Module)and Consumers (Views)
 */
@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        ActivityModule::class
    ]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: WeatherApp)
}