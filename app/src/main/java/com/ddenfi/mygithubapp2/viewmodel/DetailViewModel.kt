package com.ddenfi.mygithubapp2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.ddenfi.mygithubapp2.database.DetailUser
import com.ddenfi.mygithubapp2.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = UserRepository(application)

    suspend fun getDetailUser(user: DetailUser) = repo.getDetailUser(user)

    fun setFavUser(user: DetailUser) = CoroutineScope(Dispatchers.IO).launch {
        repo.setFav(user, true)
    }

    fun deleteFavUser(data: DetailUser) =
        CoroutineScope(Dispatchers.IO).launch { repo.deleteFavUser(data) }

}