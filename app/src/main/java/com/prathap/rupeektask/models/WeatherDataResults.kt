package com.prathap.rupeektask.models
/***
 *  wrapper for Forecast data and error handling
 */
data class WeatherDataResults(
        val success: Boolean,
        val error: String = "",
        val data: WeatherResponse? = null
)