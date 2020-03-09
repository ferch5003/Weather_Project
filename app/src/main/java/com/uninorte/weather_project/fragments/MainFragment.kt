package com.uninorte.weather_project.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager

import com.uninorte.weather_project.R
import com.uninorte.weather_project.adapters.WeatherAdapter
import com.uninorte.weather_project.data.CurrentWeather
import com.uninorte.weather_project.models.Weather
import com.uninorte.weather_project.viewmodels.CurrentWeatherViewModel
import kotlinx.android.synthetic.main.fragment_main.view.*

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment(), WeatherAdapter.onListInteraction {

    private val weathers = mutableListOf<Weather>()
    private lateinit var viewModel: CurrentWeatherViewModel
    lateinit var navController: NavController
    private var adapter: WeatherAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val idCities = mutableListOf("3688689","3674962","3687925","3689147","3687238",
            "3667905","3685533","3667849","3688465","3688928")

        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_main, container, false)

        viewModel = ViewModelProvider(this).get(CurrentWeatherViewModel::class.java)

        for(id in idCities){
            viewModel.addCurrentWeather(id)
        }

        viewModel.getCurrentWeathers().observe(viewLifecycleOwner, Observer { obsCW ->
            run{
                loadData(obsCW)
            }
        })

        adapter = WeatherAdapter(weathers, this)

        view.weather_list.layoutManager = LinearLayoutManager(context)
        view.weather_list.adapter = adapter

        return view
    }

    private fun loadData(obsCW: List<CurrentWeather>){
        for(randWeather in obsCW){
            var icon = randWeather.weather.first().icon

            var weather = Weather(
                randWeather.id.toString(),
                randWeather.name,
                randWeather.main.temp,
                "http://openweathermap.org/img/wn/${icon}@2x.png"
            )

            if(!weathers.contains(weather)){
                weathers.add(weather)
            }
        }
        adapter!!.updateData()
    }

    override fun onListCardInteraction(item: Weather?) {
        val bundle = bundleOf("data" to item, "user" to item)
        navController.navigate(R.id.forecastFragment,bundle)
    }
}
