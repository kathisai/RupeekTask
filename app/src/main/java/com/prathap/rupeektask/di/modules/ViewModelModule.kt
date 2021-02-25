package com.prathap.rupeektask.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.prathap.rupeektask.di.ViewModelKey
import com.prathap.rupeektask.utils.AppViewModelFactory
import com.prathap.rupeektask.ui.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * For all ViewModel Injections
 */
@SuppressWarnings("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindhomeViewModel(homeViewModel: HomeViewModel): ViewModel

    /**
     * To Inject View Model to any class we need @ViewModelProvider
     * Note: This depends on  {@link AppViewModelFactory}
     */
    @Binds
    abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory
}