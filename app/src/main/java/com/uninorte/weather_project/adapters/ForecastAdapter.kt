package com.uninorte.weather_project.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.uninorte.weather_project.R
import com.uninorte.weather_project.databinding.ClimateForecastRowBinding
import com.uninorte.weather_project.models.Forecast

class ForecastAdapter(private val mValues: List<Forecast>)
    : RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ForecastAdapter.ViewHolder {
        // val view = LayoutInflater.from(parent.context).inflate(R.layout.row, parent,false)
        // return ViewHolder(view)
        var binder: ClimateForecastRowBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.climate_forecast_row, parent, false)
        return ViewHolder(binder)
    }

    override fun getItemCount(): Int = mValues.size!!

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mView.forecast = item
        holder.mView.executePendingBindings()

    }

    fun updateData(){
        notifyDataSetChanged()
    }

    inner class ViewHolder(val mView: ClimateForecastRowBinding): RecyclerView.ViewHolder(mView.root){

    }

}