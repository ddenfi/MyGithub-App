package com.ddenfi.mygithubapp2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ddenfi.mygithubapp2.database.DetailUser
import com.ddenfi.mygithubapp2.networking.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowViewModel : ViewModel() {
    private val _users = MutableLiveData<ArrayList<DetailUser>>()
    val users: LiveData<ArrayList<DetailUser>> = _users

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getFollowers(username: String?) {
        val client = ApiConfig.getApiService().getFollowersUser(username)
        _isLoading.value = true

        client.enqueue(object : Callback<ArrayList<DetailUser>> {
            override fun onResponse(
                call: Call<ArrayList<DetailUser>>,
                response: Response<ArrayList<DetailUser>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _users.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<ArrayList<DetailUser>>, t: Throwable) {
                Log.d("FollowersResponse", "${t.message}")
            }
        })
    }

    fun getFollowing(username: String?) {
        val client = ApiConfig.getApiService().getFollowingUsers(username)
        _isLoading.value = true

        client.enqueue(object : Callback<ArrayList<DetailUser>> {
            override fun onResponse(
                call: Call<ArrayList<DetailUser>>,
                response: Response<ArrayList<DetailUser>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _users.postValue(response.body())
                    Log.d("FollowersResponse", "${response.body()}")
                }
            }

            override fun onFailure(call: Call<ArrayList<DetailUser>>, t: Throwable) {
                Log.d("FollowersResponse", "${t.message}")
            }
        })
    }

}