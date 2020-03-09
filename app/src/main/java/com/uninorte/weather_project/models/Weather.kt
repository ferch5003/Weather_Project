package com.uninorte.weather_project.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Weather (
    val id: String,
    val city: String,
    val currentWeather: Double,
    val thumbnail: String
): Parcelable {
}