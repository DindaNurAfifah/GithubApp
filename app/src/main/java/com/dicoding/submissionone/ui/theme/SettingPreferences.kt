package com.dicoding.submissionone.ui.theme

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.prefDataStore by preferencesDataStore("settings")

class SettingPreferences constructor(context: Context) {

    private val THEME_KEY = booleanPreferencesKey("theme_setting")
    private val settingPreferences = context.prefDataStore

    fun getThemeSetting(): Flow<Boolean> =
        settingPreferences.data.map { preferences ->
            preferences[THEME_KEY] ?: false
        }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        settingPreferences.edit { preferences ->
            preferences[THEME_KEY] = isDarkModeActive
        }
    }
}