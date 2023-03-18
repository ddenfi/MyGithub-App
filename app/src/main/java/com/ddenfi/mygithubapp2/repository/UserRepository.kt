package com.ddenfi.mygithubapp2.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.ddenfi.mygithubapp2.database.DetailUser
import com.ddenfi.mygithubapp2.database.SettingPreferences
import com.ddenfi.mygithubapp2.database.UserDao
import com.ddenfi.mygithubapp2.database.UserDatabase
import com.ddenfi.mygithubapp2.networking.ApiConfig
import com.ddenfi.mygithubapp2.networking.ApiService
import com.ddenfi.mygithubapp2.utils.Results
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(application: Application) {
    private val mUserDao: UserDao
    private val retrofit: ApiService
    private val dataStore: SettingPreferences


    init {
        val db = UserDatabase.getDatabase(application)
        mUserDao = db.userDao()
        retrofit = ApiConfig.getApiService()
        dataStore = SettingPreferences.getInstance(application)
    }

    fun getAllFavUser(): LiveData<List<DetailUser>> {
        return mUserDao.getAllFavUser()
    }

    suspend fun setFav(user: DetailUser, isFavState: Boolean) {
        user.isFav = isFavState
        mUserDao.insertFavUser(user)
    }

    suspend fun deleteFavUser(user: DetailUser) = mUserDao.deleteFavUser(user)

    suspend fun getDetailUser(data: DetailUser): LiveData<Results<DetailUser>> {
        val result = MutableLiveData<Results<DetailUser>>()

        if (mUserDao.isNewsBookmarked(data.id)) {
            result.postValue(Results.Success(mUserDao.getUser(data.id)))
        } else {
            result.postValue(Results.Loading())
            retrofit.getDetailUser(data.login).enqueue(object : Callback<DetailUser> {
                override fun onResponse(call: Call<DetailUser>, response: Response<DetailUser>) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            result.postValue(Results.Success(response.body()))
                        } else {
                            result.postValue(Results.Error("Filed Kosong"))
                        }
                    }
                }

                override fun onFailure(call: Call<DetailUser>, t: Throwable) {
                    result.postValue(Results.Error(t.message))
                    Log.e("DetailViewModel", "onFailure searchUsers ${t.message}")
                }
            })
        }
        return result
    }

    suspend fun saveThemeSetting(isDarkMode: Boolean) = dataStore.saveThemeSetting(isDarkMode)

    fun getThemeSetting() = dataStore.getThemeSetting()

}