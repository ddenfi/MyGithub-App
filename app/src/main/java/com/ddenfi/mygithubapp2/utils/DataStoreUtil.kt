package com.ddenfi.mygithubapp2.utils

import androidx.datastore.preferences.core.booleanPreferencesKey

object DataStoreUtil {

    const val SETTING_DATA_STORE_NAME = "SETTING_DATASTORE"
    val THEME_KEY = booleanPreferencesKey("THEME_PREF_KEY")
}