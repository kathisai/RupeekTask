package com.prathap.rupeektask.ui.bindings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.prathap.rupeektask.R
import com.prathap.rupeektask.models.WeatherData
import kotlinx.android.synthetic.main.list_item_home_city.view.*
import kotlin.collections.ArrayList

class HomeListAdapter(private val cityWeather: ArrayList<WeatherData>) :
        RecyclerView.Adapter<HomeListAdapter.ViewHolder>() {
    /**
     * user inner to defind inner calls in parent class
     */
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(weatherData: WeatherData) {
            itemView.tvTemp.text = weatherData.temp.toString()
            itemView.tvRain.text = weatherData.rain.toString()
            val sdf = java.text.SimpleDateFormat("MM-dd-yyyy")
            val date = java.util.Date(weatherData.time.toLong() * 1000)
            itemView.tvDate.text = sdf.format(date)
            itemView.tvWind.text = weatherData.wind.toString()

        }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.list_item_home_city, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(cityWeather[position])

    }

    override fun getItemCount() = cityWeather.size
}
