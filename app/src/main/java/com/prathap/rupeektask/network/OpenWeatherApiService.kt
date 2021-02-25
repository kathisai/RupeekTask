package com.prathap.rupeektask.network


import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.prathap.rupeektask.models.WeatherResponse
import com.prathap.rupeektask.utils.Constants
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Retrofit Service
 */
interface WeatherApiService {

    @GET("5d3a99ed2f0000bac16ec13a")
    fun featchWeatherReport(
    ): Deferred<WeatherResponse>


    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): WeatherApiService {

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherApiService::class.java)
        }
    }
}