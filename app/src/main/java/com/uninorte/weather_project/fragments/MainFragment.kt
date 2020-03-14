package com.uninorte.weather_project.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

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

    private lateinit var viewModel: CurrentWeatherViewModel
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var navController: NavController
    private val weathers = mutableListOf<Weather>()
    private var adapter: WeatherAdapter? = null
    private var idCities = mutableListOf<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        idCities = mutableListOf("3688689","3674962","3687925","3689147","3687238",
            "3667905","3685533","3667849","3688465","3688928")

        viewModel = ViewModelProvider(this).get(CurrentWeatherViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_main, container, false)

        recyclerView = view.weather_list

        swipeRefreshLayout = view.swipe_refresh_layout

        swipeRefreshLayout.isRefreshing = true

        adapter = WeatherAdapter(weathers, this)

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.deleteCurrentWeathers()
            addCurrentWeather()
        }

        viewModelAct()

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        return view
    }

    private fun viewModelAct(){
        addCurrentWeather()

        viewModel.getCurrentWeathers().removeObserver { obsCW ->
            run{
                weathers.clear()
                loadData(obsCW)
                if(swipeRefreshLayout.isRefreshing){
                    swipeRefreshLayout.isRefreshing = false
                }
            }
        }

        viewModel.getCurrentWeathers().observe(viewLifecycleOwner, Observer { obsCW ->
            run{
                weathers.clear()
                loadData(obsCW)
                if(swipeRefreshLayout.isRefreshing){
                    swipeRefreshLayout.isRefreshing = false
                }
            }
        })
    }

    private fun addCurrentWeather(){
        for(id in idCities){
            viewModel.addCurrentWeather(id)
        }
    }

    private fun loadData(obsCW: List<CurrentWeather>){
        for(cityWeather in obsCW){
            val currentWeather = cityWeather.weather.first()

            val color = getColorString(currentWeather.main)

            val weather = Weather(
                cityWeather.id.toString(),
                cityWeather.name,
                cityWeather.main.temp,
                currentWeather.main,
                currentWeather.description,
                color,
                "http://openweathermap.org/img/wn/${currentWeather.icon}@2x.png"
            )

            if(!weathers.contains(weather)){
                weathers.add(weather)
            }
        }
        adapter!!.updateData()
        animation(recyclerView)
    }

    private fun animation(recyclerView: RecyclerView){
        val context: Context = recyclerView.context

        val resId: Int = R.anim.layout_animation_fall_down

        val animation: LayoutAnimationController =
            AnimationUtils.loadLayoutAnimation(context, resId)

        recyclerView.layoutAnimation = animation
        recyclerView.scheduleLayoutAnimation()
    }

    private fun getColorString(main: String): String{
        return when(main) {
            "Clear" -> "#fff176"
            "Clouds", "Mist", "Haze", "Smoke", "Fog" -> "#e0e0e0"
            "Rain", "Thunderstorm", "Drizzle", "Snow", "Tornado", "Squall"-> "#b3e5fc"
            "Dust", "Sand", "Ash" -> "#a1887f"
            else -> { // Note the block
                "#ffffff"
            }
        }
    }

    override fun onListCardInteraction(item: Weather?) {
        val bundle = bundleOf("data" to item, "weather" to item)
        navController.navigate(R.id.forecastFragment,bundle)
    }
}
