package com.ddenfi.mygithubapp2.database

import android.annotation.SuppressLint
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.ddenfi.mygithubapp2.utils.DataStoreUtil
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class SettingPreferences(private val context: Context) {


    private val Context.userPreferenceDataStore: DataStore<Preferences> by preferencesDataStore(
        name = DataStoreUtil.SETTING_DATA_STORE_NAME
    )

    suspend fun saveThemeSetting(isDarkMode: Boolean) {
        context.userPreferenceDataStore.edit {
            it[DataStoreUtil.THEME_KEY] = isDarkMode
        }
    }

    fun getThemeSetting(): Flow<Boolean> =
        context.userPreferenceDataStore.data.map {
            it[DataStoreUtil.THEME_KEY] ?: false
        }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: SettingPreferences? = null

        fun getInstance(context: Context): SettingPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingPreferences(context)
                INSTANCE = instance
                instance
            }
        }
    }
}