package com.uninorte.weather_project.fragments

import  android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.uninorte.weather_project.R
import com.uninorte.weather_project.adapters.ForecastAdapter
import com.uninorte.weather_project.adapters.WeatherAdapter
import com.uninorte.weather_project.data.ClimateForecast
import com.uninorte.weather_project.data.CurrentWeather
import com.uninorte.weather_project.databinding.FragmentForecastBinding
import com.uninorte.weather_project.models.Forecast
import com.uninorte.weather_project.models.Weather
import com.uninorte.weather_project.viewmodels.ClimateForecastViewModel
import com.uninorte.weather_project.viewmodels.CurrentWeatherViewModel
import kotlinx.android.synthetic.main.fragment_main.view.*

/**
 * A simple [Fragment] subclass.
 */
class ForecastFragment : Fragment() {

    lateinit var weather: Weather
    private val forecasts = mutableListOf<Forecast>()
    private lateinit var viewModel: ClimateForecastViewModel
    lateinit var mBinding: FragmentForecastBinding
    private var adapter: ForecastAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_forecast, container, false)

        adapter = ForecastAdapter(forecasts)

        mBinding.forecastList.layoutManager = LinearLayoutManager(context)
        mBinding.forecastList.adapter = adapter

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weather = arguments!!.getParcelable("weather")!!
        mBinding.weather = weather

        viewModel = ViewModelProvider(this).get(ClimateForecastViewModel::class.java)

        viewModel.addClimateForecast(weather.id)

        viewModel.getClimateForecasts().observe(viewLifecycleOwner, Observer { obsCF ->
            run{
                loadData(obsCF)
            }
        })
    }

    private fun loadData(obsCF: List<ClimateForecast>){
        for((i,cityForecast) in obsCF.withIndex()){
            var icon = cityForecast.weather.first().icon

            var forecast = Forecast(
                "Day ${i + 1}",
                cityForecast.main.temp,
                "http://openweat" +
                        "hermap.org/img/wn/${icon}@2x.png"
            )

            if(!forecasts.contains(forecast)){
                forecasts.add(forecast)
            }
        }
        adapter!!.updateData()
    }

}
