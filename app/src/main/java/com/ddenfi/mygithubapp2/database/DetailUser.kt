package com.ddenfi.mygithubapp2.database


import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "user")
data class DetailUser(

    @field:SerializedName("id")
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int? = null,

    @field:SerializedName("name")
    @ColumnInfo(name = "name")
    val name: String? = null,

    @field:SerializedName("login")
    @ColumnInfo(name = "login")
    val login: String? = null,

    @field:SerializedName("type")
    @ColumnInfo(name = "type")
    val type: String? = null,

    @field:SerializedName("company")
    @ColumnInfo(name = "company")
    val company: String? = null,

    @field:SerializedName("public_repos")
    @ColumnInfo(name = "public?_repos")
    val publicRepos: Int? = null,

    @field:SerializedName("followers")
    @ColumnInfo(name = "followers")
    val followers: Int? = null,

    @field:SerializedName("avatar_url")
    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String? = null,

    @field:SerializedName("following")
    @ColumnInfo(name = "following")
    val following: Int? = null,

    @field:SerializedName("location")
    @ColumnInfo(name = "location")
    val location: String? = null,

    @ColumnInfo(name = "is_favorite")
    var isFav: Boolean = false,

    ) : Parcelable
