package com.ddenfi.mygithubapp2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ddenfi.mygithubapp2.repository.UserRepository

class FavViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = UserRepository(application)

    fun getFavUser() = repo.getAllFavUser()
}