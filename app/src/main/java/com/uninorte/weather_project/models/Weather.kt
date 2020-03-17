package com.uninorte.weather_project.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Weather (
    val id: String,
    val city: String,
    val currentWeather: Double,
    val mainWeather: String,
    val descriptionWeather: String,
    val humidity: Int,
    val dt: Long,
    val background: Int,
    val thumbnail: String
): Parcelable {
}