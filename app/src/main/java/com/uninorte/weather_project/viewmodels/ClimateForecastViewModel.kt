package com.uninorte.weather_project.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.uninorte.weather_project.dao.ClimateForecastDao
import com.uninorte.weather_project.data.ClimateForecast

class ClimateForecastViewModel (application: Application) : AndroidViewModel(application) {

    private var climateForecastDao: ClimateForecastDao = ClimateForecastDao.getInstance(this.getApplication())

    fun addClimateForecast(id: String) {
        climateForecastDao.addClimateForecast(id)
    }

    fun getClimateForecasts(): MutableLiveData<List<ClimateForecast>> {
        return climateForecastDao.getClimateForecasts()
    }
}