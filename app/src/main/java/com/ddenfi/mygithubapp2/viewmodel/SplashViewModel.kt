package com.ddenfi.mygithubapp2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import com.ddenfi.mygithubapp2.repository.UserRepository
import kotlinx.coroutines.Dispatchers

class SplashViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = UserRepository(application)

    fun getThemeSetting() = repo.getThemeSetting().asLiveData(Dispatchers.IO)
}