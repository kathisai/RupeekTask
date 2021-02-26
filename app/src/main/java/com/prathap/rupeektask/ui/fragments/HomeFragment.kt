package com.prathap.rupeektask.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import com.prathap.rupeektask.R
import com.prathap.rupeektask.di.Injectable
import com.prathap.rupeektask.models.WeatherData
import com.prathap.rupeektask.ui.HomeViewModel
import com.prathap.rupeektask.ui.bindings.HomeListAdapter
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

    lateinit var mLocationRequest: LocationRequest
    private var mFusedLocationClient: FusedLocationProviderClient? = null

    private var mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val locationList = locationResult.locations
            if (locationList.isNotEmpty()) {
                val location = locationList.last()
                val latLng = LatLng(location.latitude, location.longitude)
                currentLocation.visibility = View.VISIBLE
                currentLocation.text = String.format(resources.getString(R.string.current_location), latLng.latitude.toString(), latLng.longitude.toString());
            }
        }
    }

    override fun onPause() {
        super.onPause()
        mFusedLocationClient?.removeLocationUpdates(mLocationCallback)
    }

    override fun onResume() {
        super.onResume()
        requestPermissionsForApp()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mFusedLocationClient = context?.let { LocationServices.getFusedLocationProviderClient(it) }
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

    private fun requestPermissionsForApp() {
        context?.let {
            if (ActivityCompat.checkSelfPermission(
                            it,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(
                            it,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
            ) {
                // This condition only becomes true if the user has denied the permission previously
                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    showRationaleDialog(
                            getString(R.string.rationale_title),
                            getString(R.string.rationale_desc),
                            LOCATION_PERMISSION_REQUEST_CODE
                    )
                } else {
                    // Perform a permission check
                    requestPermissions(
                            getLocationPermission(),
                            LOCATION_PERMISSION_REQUEST_CODE
                    )
                }
            } else {
                requestLocation()
            }

        }

    }

    @SuppressLint("MissingPermission")
    private fun requestLocation() {
        mLocationRequest = LocationRequest()
        mLocationRequest.interval = 30000
        mLocationRequest.fastestInterval = 30000
        mLocationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        mFusedLocationClient?.requestLocationUpdates(
                mLocationRequest,
                mLocationCallback,
                Looper.myLooper()
        )

    }

    private fun getLocationPermission(): Array<String> {
        return arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }


    override fun onRequestPermissionsResult(
            requestCode: Int,
            @NonNull permissions: Array<String>,
            @NonNull grantResults: IntArray
    ) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                requestLocation()
            } else {
                requestPermissionsForApp()
            }
        }
    }

    companion object {
        // to handle permission request
        const val LOCATION_PERMISSION_REQUEST_CODE = 102
    }

    /**
     * Shows rationale dialog for displaying why the app needs permission
     * Only shown if the user has denied the permission request previously
     */
    private fun showRationaleDialog(
            title: String,
            message: String,
            requestCode: Int
    ) {
        val builder: AlertDialog.Builder = activity?.let { AlertDialog.Builder(it) }!!
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("Ok") { _, _ ->
                    requestPermissions(getLocationPermission(), requestCode)
                }
        builder.create().show()
    }

}