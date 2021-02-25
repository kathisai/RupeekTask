package com.prathap.rupeektask.models

import com.google.gson.annotations.SerializedName


data class WeatherData (

	@SerializedName("temp") val temp : Int,
	@SerializedName("time") val time : Int,
	@SerializedName("rain") val rain : Int,
	@SerializedName("wind") val wind : Int
)