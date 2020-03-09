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
import com.uninorte.weather_project.data.ClimateForecast
import org.json.JSONObject

class ClimateForecastDao private constructor(var context: Context) {

    private val climateForecasts = MutableLiveData<List<ClimateForecast>>()
    private val climateForecastsList = mutableListOf<ClimateForecast>()
    private var queue: RequestQueue = VolleySingleton.getInstance(context).requestQueue

    companion object{
        @Volatile
        private var INSTANCE: ClimateForecastDao? = null
        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: ClimateForecastDao(context).also { INSTANCE = it }
            }
    }

    fun addClimateForecast(id: String) {
        VolleySingleton.getInstance(context).addToRequestQueue(getJsonObject(id))
    }

    fun getClimateForecasts() = climateForecasts

    private fun getJsonObject(id: String): JsonObjectRequest {
        val url = ""

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
        var list = ClimateForecast.getClimateForecast(response)
        val size: Int = list.size
        for (i in 0 until size) {
            val climateForecast = list[i]
            climateForecastsList.add(climateForecast)
        }
        climateForecasts.value = climateForecastsList
    }
}