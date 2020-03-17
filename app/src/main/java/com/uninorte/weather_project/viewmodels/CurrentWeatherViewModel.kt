package com.uninorte.weather_project.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.uninorte.weather_project.dao.CurrentWeatherDao
import com.uninorte.weather_project.data.CurrentWeather

class CurrentWeatherViewModel(application: Application) : AndroidViewModel(application) {

    private var currentWeatherDao: CurrentWeatherDao = CurrentWeatherDao.getInstance(this.getApplication())

    fun addCurrentWeathers(ids: String) {
        currentWeatherDao.addCurrentWeathers(ids)
    }

    fun getCurrentWeathers(): MutableLiveData<List<CurrentWeather>> {
        return currentWeatherDao.getCurrentWeathers()
    }

    fun deleteCurrentWeathers(){
        currentWeatherDao.deleteCurrentWeathers()
    }
}