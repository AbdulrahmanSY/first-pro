package com.example.maptask.dataLayer.model

import okhttp3.ResponseBody

sealed class Resource<T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<T>(val error: String) : Resource<Nothing>()
    class Loading<T> : Resource<T>()
}