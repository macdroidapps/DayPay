package ru.macdroid.daypay.utils

sealed class NetworkState<T>(val data: T? = null, val message: String? = null, val code: Int? = null) {
    class Success<T>(data: T?) : NetworkState<T>(data)
    class Error<T>(message: String, data: T? = null, code: Int? = null) : NetworkState<T>(data, message, code)
    class Loading<T>(data: T? = null) : NetworkState<T>(data)
}