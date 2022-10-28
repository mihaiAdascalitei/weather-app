package com.madsq.weather.base

import android.app.Application
import com.madsq.weather.presentation.darktheme.DarkThemeUtil

/**
 * Created by mihai.adascalitei on 24.10.2022.
 */

class WeatherApp : Application() {

    override fun onCreate() {
        super.onCreate()
        DarkThemeUtil.init(this)
    }
}