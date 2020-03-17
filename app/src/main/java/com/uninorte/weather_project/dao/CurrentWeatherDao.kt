package com.uninorte.weather_project.dao

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.uninorte.weather_project.BuildConfig
import com.uninorte.weather_project.VolleySingleton
import com.uninorte.weather_project.data.CurrentWeather
import org.json.JSONObject

class CurrentWeatherDao private constructor(var context: Context) {

    private val currentWeathers = MutableLiveData<List<CurrentWeather>>()
    private val currentWeatherList = mutableListOf<CurrentWeather>()
    private var queue: RequestQueue = VolleySingleton.getInstance(context).requestQueue

    companion object{
        @Volatile
        private var INSTANCE: CurrentWeatherDao? = null
        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: CurrentWeatherDao(context).also { INSTANCE = it }
            }
    }

    fun addCurrentWeathers(ids: String) {
        if(currentWeatherList.size < 9) VolleySingleton.getInstance(context).addToRequestQueue(getJsonObject(ids))
    }

    fun getCurrentWeathers() = currentWeathers

    fun deleteCurrentWeathers(){
        currentWeatherList.clear()
    }

    private fun getJsonObject(ids: String): JsonObjectRequest {
        val url = "${BuildConfig.BASE_URL}group?appid=${BuildConfig.API_KEY}&id=${ids}&units=metric&lang=es"

        return JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                parseObjectG(response)
            },
            Response.ErrorListener { error->
                Log.d("WebRequestTest", "That didn't work ${error.message}")
            }
        )
    }

    private fun parseObjectG(response: JSONObject) {
        val list = CurrentWeather.getCurrentWeather(response)
        val size: Int = list.size
        for (i in 0 until size) {
            val currentWeather = list[i]
            currentWeatherList.add(currentWeather)
        }
        currentWeathers.value = currentWeatherList
    }

}