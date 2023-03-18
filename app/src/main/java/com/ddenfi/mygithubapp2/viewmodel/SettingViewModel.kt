package com.ddenfi.mygithubapp2.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.ddenfi.mygithubapp2.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = UserRepository(application)

    fun saveThemeSetting(isDarkMode: Boolean) = viewModelScope.launch {
        repo.saveThemeSetting(isDarkMode)
    }

    fun getThemeSetting() = repo.getThemeSetting().asLiveData(Dispatchers.IO)

}