package com.prathap.rupeektask.models

import com.google.gson.annotations.SerializedName


data class WeatherResponse (
	@SerializedName("data") val data : List<WeatherData>
)