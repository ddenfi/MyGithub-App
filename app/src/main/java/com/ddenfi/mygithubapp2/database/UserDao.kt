package com.ddenfi.mygithubapp2.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavUser(user: DetailUser)

    @Delete
    suspend fun deleteFavUser(user: DetailUser)

    @Query("SELECT * from user ORDER BY id ASC")
    fun getAllFavUser(): LiveData<List<DetailUser>>

    @Query("SELECT * FROM user WHERE id = :id")
    suspend fun getUser(id: Int?): DetailUser

    @Query("SELECT EXISTS(SELECT * FROM user WHERE id = :id AND is_favorite = 1)")
    suspend fun isNewsBookmarked(id: Int?): Boolean

}