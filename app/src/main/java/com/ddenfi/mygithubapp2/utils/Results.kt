package com.ddenfi.mygithubapp2.utils

sealed class Results<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?) : Results<T>(data)
    class Error<T>(message: String?=null, data: T? = null) : Results<T>(data, message)
    class Loading<T>(data: T? = null) : Results<T>(data)
}