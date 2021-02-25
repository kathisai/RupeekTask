package com.prathap.rupeektask.ui


import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prathap.rupeektask.models.WeatherDataResults
import com.prathap.rupeektask.repository.OpenWeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Shared ViewModel for all fragments
 */
 class HomeViewModel @Inject constructor(
    private val repository: OpenWeatherRepository
) : ViewModel() {

    var loading = MutableLiveData<Int>()

    private val _dataResults = MutableLiveData<WeatherDataResults>()
    val dataResults: LiveData<WeatherDataResults>
        get() = _dataResults



    /**
     * Fetch data from API
     */
    fun getData() {
        loading.value = View.VISIBLE
        GlobalScope.launch(Dispatchers.IO) {
            val weatherDataResults = repository.getForeCast()
            _dataResults.postValue(weatherDataResults)
            loading.postValue(View.GONE)
        }
    }




}