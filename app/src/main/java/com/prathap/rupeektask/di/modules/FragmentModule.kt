package com.prathap.rupeektask.di.modules

import com.prathap.rupeektask.ui.fragments.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * For all Fragment Injections
 * Note: we need to implement Injectable for all fragment's which need Injection
 */
@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

}