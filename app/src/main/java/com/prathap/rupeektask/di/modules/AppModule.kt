package com.prathap.rupeektask.di.modules

import android.app.Application
import com.prathap.rupeektask.network.ConnectivityInterceptor
import com.prathap.rupeektask.network.WeatherApiService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * * for all Generic App Injection
 */
@Module(includes = [ViewModelModule::class])
class AppModule {

    @Singleton
    @Provides
    fun getConnectivityInterceptor(app: Application): ConnectivityInterceptor {
        return ConnectivityInterceptor(app.applicationContext)
    }



    @Singleton
    @Provides
    fun getRetrofitService(
        connectivityInterceptor: ConnectivityInterceptor
    ): WeatherApiService {
        return WeatherApiService.invoke(
            connectivityInterceptor = connectivityInterceptor
        )
    }

}