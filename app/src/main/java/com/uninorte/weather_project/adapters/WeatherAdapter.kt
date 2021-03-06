package com.uninorte.weather_project.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.uninorte.weather_project.R
import com.uninorte.weather_project.databinding.CurrentWeatherRowBinding
import com.uninorte.weather_project.models.Weather

class WeatherAdapter(private val mValues: List<Weather>,
                  private val mListener: onListInteraction)
    : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binder: CurrentWeatherRowBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.current_weather_row, parent, false)
        return ViewHolder(binder)
    }

    override fun getItemCount(): Int = mValues.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mView.cardLayout.setBackgroundResource(item.background)
        holder.mView.weather = item
        holder.mView.executePendingBindings()

        holder.mView.cardWeather.setOnClickListener{
            mListener.onListCardInteraction(item)
        }
    }

    fun updateData(){
        notifyDataSetChanged()
    }

    inner class ViewHolder(val mView: CurrentWeatherRowBinding): RecyclerView.ViewHolder(mView.root)

    interface onListInteraction{
        fun onListCardInteraction(item: Weather?)
    }
}