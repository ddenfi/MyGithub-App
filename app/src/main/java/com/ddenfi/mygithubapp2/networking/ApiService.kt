package com.ddenfi.mygithubapp2.networking


import com.ddenfi.mygithubapp2.database.DetailUser
import com.ddenfi.mygithubapp2.database.SearchUserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<SearchUserResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String?
    ): Call<DetailUser>

    @GET("users/{username}/followers")
    fun getFollowersUser(
        @Path("username") username: String?
    ): Call<ArrayList<DetailUser>>

    @GET("users/{username}/following")
    fun getFollowingUsers(
        @Path("username") username: String?
    ): Call<ArrayList<DetailUser>>


}