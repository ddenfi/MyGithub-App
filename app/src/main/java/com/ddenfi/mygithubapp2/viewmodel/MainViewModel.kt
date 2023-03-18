package com.ddenfi.mygithubapp2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ddenfi.mygithubapp2.database.DetailUser
import com.ddenfi.mygithubapp2.database.SearchUserResponse
import com.ddenfi.mygithubapp2.networking.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _users = MutableLiveData<ArrayList<DetailUser>>()
    val users: LiveData<ArrayList<DetailUser>> = _users

    private val _isLoading = MutableLiveData(true)
    val isLoading: LiveData<Boolean> = _isLoading

    fun searchUser(query: String) {
        val client = ApiConfig.getApiService().getSearchUsers(query)
        _isLoading.value = true

        client.enqueue(object : Callback<SearchUserResponse> {
            override fun onResponse(
                call: Call<SearchUserResponse>,
                response: Response<SearchUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _users.postValue(response.body()?.items)
                }
            }

            override fun onFailure(call: Call<SearchUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("MainViewModel", "onFailure searchUsers ${t.message}")
            }
        })
    }

    fun cancelSearch(){
        _users.value = ArrayList()
        _isLoading.value = true
    }
}