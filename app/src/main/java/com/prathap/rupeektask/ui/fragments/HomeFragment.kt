package com.prathap.rupeektask.ui.fragments

import android.content.Context.LOCATION_SERVICE
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.prathap.rupeektask.R
import com.prathap.rupeektask.di.Injectable
import com.prathap.rupeektask.models.WeatherData
import com.prathap.rupeektask.ui.bindings.HomeListAdapter
import com.prathap.rupeektask.ui.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.loading_view.*
import javax.inject.Inject


/**
 * Initial Fragment to show list of bookmarked cities
 */
class HomeFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: HomeViewModel by viewModels {
        viewModelFactory
    }
    private val cityWeather = ArrayList<WeatherData>()
    private val cityAdapter = HomeListAdapter(cityWeather)

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_list_weather.layoutManager = LinearLayoutManager(activity)
        rv_list_weather.adapter = cityAdapter



        viewModel.loading.observe(viewLifecycleOwner, Observer {
            loadingView.visibility = it
        })
        viewModel.dataResults.observe(viewLifecycleOwner, Observer { weatherResults ->
            if (weatherResults.success) {
                weatherResults.data?.let { weatherForecast ->
                    cityWeather.clear()
                    cityWeather.addAll(weatherForecast.data)
                    cityAdapter.notifyDataSetChanged()
                    emptyView.visibility = View.GONE
                } ?: run {
                    showError()
                }
            } else {
                showEmptyView()
            }
        })
        viewModel.getData();

    }

    private fun showError() {
        context?.let {
            Toast.makeText(it, getString(R.string.genrial_error_message), Toast.LENGTH_SHORT).show()
        }
    }

    private fun showEmptyView() {
        emptyView.visibility = View.VISIBLE
    }
}