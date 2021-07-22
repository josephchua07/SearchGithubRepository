package com.chua.githubsearch.model

sealed class Response<out T> {
    data class Success<out T>(val data: T) : Response<T>()
    data class GenericError(val message: String) : Response<Nothing>()
}
