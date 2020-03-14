package com.uninorte.weather_project.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Forecast (
    val day: String,
    val expectedWeather: Double,
    val mainWeather: String,
    val descriptionWeather: String,
    val color: String,
    val thumbnail: String
): Parcelable {
}