package com.example.movieapp.util

sealed class NetworkResult<T>(
    val data: T? = null,
    val message: String? = null
) {
    // Override equals method to compare the contents of NetworkResult.Success
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NetworkResult<*>

        if (data != other.data) return false
        if (message != other.message) return false

        return true
    }
    class Success<T>(data: T) : NetworkResult<T>(data)
    class Error<T>(message: String, data: T? = null) : NetworkResult<T>(data, message)
}