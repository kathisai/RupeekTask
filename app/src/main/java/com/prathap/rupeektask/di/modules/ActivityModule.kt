package com.prathap.rupeektask.di.modules

import com.prathap.rupeektask.ui.HomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * for all Activity Injection
 */
@Suppress("unused")
@Module
abstract class ActivityModule {
    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun contributeHomeActivity(): HomeActivity
}