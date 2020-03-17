package com.uninorte.weather_project.fragments

import android.content.Context
import  android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.uninorte.weather_project.R
import com.uninorte.weather_project.adapters.ForecastAdapter
import com.uninorte.weather_project.data.ClimateForecast
import com.uninorte.weather_project.databinding.FragmentForecastBinding
import com.uninorte.weather_project.models.Forecast
import com.uninorte.weather_project.models.Weather
import com.uninorte.weather_project.viewmodels.ClimateForecastViewModel
import kotlinx.android.synthetic.main.fragment_forecast.view.*


/**
 * A simple [Fragment] subclass.
 */
class ForecastFragment : Fragment() {

    private lateinit var viewModel: ClimateForecastViewModel
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    lateinit var weather: Weather
    private val forecasts = mutableListOf<Forecast>()
    lateinit var mBinding: FragmentForecastBinding
    private var adapter: ForecastAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_forecast, container, false)

        mBinding.backToCw.setOnClickListener {
            activity?.onBackPressed()
        }

        swipeRefreshLayout = mBinding.swipeRefreshLayout

        swipeRefreshLayout.isRefreshing = true

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.addClimateForecast(weather.id)
        }

        adapter = ForecastAdapter(forecasts)

        mBinding.forecastList.layoutManager = LinearLayoutManager(context)
        mBinding.forecastList.adapter = adapter

        return mBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(ClimateForecastViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weather = arguments!!.getParcelable("weather")!!
        mBinding.weather = weather

        recyclerView = view.forecast_list

        viewModelAct()
    }

    private fun viewModelAct(){
        viewModel.addClimateForecast(weather.id)

        viewModel.getClimateForecasts().removeObserver { obsCF ->
            run{
                forecasts.clear()
                loadData(obsCF,recyclerView)
                swipeRefreshLayout.isRefreshing = false
            }
        }

        viewModel.getClimateForecasts().observe(viewLifecycleOwner, Observer { obsCF ->
            run{
                forecasts.clear()
                loadData(obsCF,recyclerView)
                swipeRefreshLayout.isRefreshing = false
            }
        })
    }

    private fun loadData(obsCF: List<ClimateForecast>,recyclerView: RecyclerView){
        val chunkList = obsCF.chunked(8)

        for(cityForecast in chunkList){
            val dayForecast = cityForecast.first()

            val climateForecast = dayForecast.weather.first()

            val background = getBackgroundResource(climateForecast.main)

            val forecast = Forecast(
                dayForecast.main.temp,
                climateForecast.main,
                climateForecast.description,
                dayForecast.main.humidity,
                dayForecast.dt,
                background,
                "http://openweathermap.org/img/wn/${climateForecast.icon}@2x.png"
            )

            if(!forecasts.contains(forecast)){
                forecasts.add(forecast)
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

    private fun getBackgroundResource(main: String): Int{
        return when(main) {
            "Clear" -> R.drawable.bg1
            "Rain", "Thunderstorm", "Drizzle", "Snow", "Tornado", "Squall"-> R.drawable.bg2
            "Dust", "Sand", "Ash" -> R.drawable.bg3
            "Clouds", "Mist", "Haze", "Smoke", "Fog" -> R.drawable.bg4
            else -> {
                R.drawable.bg5
            }
        }
    }

}
