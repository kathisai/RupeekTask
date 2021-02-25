package com.prathap.rupeektask.repository


import com.prathap.rupeektask.models.WeatherDataResults
import com.prathap.rupeektask.network.WeatherApiService
import com.prathap.rupeektask.network.exceptions.BadRequestException
import com.prathap.rupeektask.network.exceptions.InternalServerException
import com.prathap.rupeektask.network.exceptions.NoConnectivityException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for all network calls
 */
@Singleton
class OpenWeatherRepository @Inject constructor(
    private val weatherApiService: WeatherApiService
) {
    suspend fun getForeCast(): WeatherDataResults {
        try {
            val weatherData = weatherApiService.featchWeatherReport(
            ).await()

            return WeatherDataResults(
                success = true,
                data = weatherData
            )
        } catch (e: Exception) {
            return WeatherDataResults(
                success = false,
                error = "IllegalStateException $e"
            )
        } catch (e: NoConnectivityException) {
            return WeatherDataResults(
                success = false,
                error = "NoConnectivityException $e"
            )
        } catch (e: BadRequestException) {
            return WeatherDataResults(
                success = false,
                error = "BadRequestException $e"

            )
        } catch (e: InternalServerException) {
            return WeatherDataResults(
                success = false,
                error = "InternalServerException $e"
            )
        } catch (e: IllegalStateException) {
            return WeatherDataResults(
                success = false,
                error = "IllegalStateException $e"
            )
        }
    }

}
