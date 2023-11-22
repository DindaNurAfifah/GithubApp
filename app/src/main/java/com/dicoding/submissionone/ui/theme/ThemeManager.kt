package com.dicoding.submissionone.ui.theme

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

object ThemeManager {
    private const val THEME_PREFERENCES = "theme_preferences"
    private const val THEME_KEY = "theme_key"

    fun applyTheme(context: Context) {
        val sharedPreferences = context.getSharedPreferences(THEME_PREFERENCES, Context.MODE_PRIVATE)
        val themeMode = sharedPreferences.getInt(THEME_KEY, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        AppCompatDelegate.setDefaultNightMode(themeMode)
    }

    fun saveTheme(context: Context, themeMode: Int) {
        val sharedPreferences = context.getSharedPreferences(THEME_PREFERENCES, Context.MODE_PRIVATE)
        sharedPreferences.edit().putInt(THEME_KEY, themeMode).apply()
    }
}