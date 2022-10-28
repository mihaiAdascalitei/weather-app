package com.madsq.weather.presentation.darktheme

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import com.madsq.weather.injection.provideSharedPrefs

/**
 * Created by mihai.adascalitei on 27.10.2022.
 */

object DarkThemeUtil {

    private const val DarkThemeModeKey = "DarkThemeModeKey"
    private lateinit var sharedPref: SharedPreferences

    fun init(ctx: Context) {
        sharedPref = provideSharedPrefs(ctx)
        AppCompatDelegate.setDefaultNightMode(darkThemeMode)
    }

    //get default dark theme mode, if not save we put -1 (default for system)
    var darkThemeMode: Int
        get() = sharedPref.getInt(DarkThemeModeKey, -1)
        set(value) {
            sharedPref.edit { putInt(DarkThemeModeKey, value) }
        }

    //update dark theme and also apply changes
    fun updateDarkThemeMode(mode: Int) {
        darkThemeMode = mode
        AppCompatDelegate.setDefaultNightMode(darkThemeMode)
    }
}