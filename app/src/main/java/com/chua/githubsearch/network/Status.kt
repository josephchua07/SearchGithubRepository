package com.chua.githubsearch.network

sealed interface Status <out T> {
    data class Success <out T>(val data: List<T>) : Status<T>
    data class Error(val message: String) : Status<Nothing>
    object Loading : Status<Nothing>
}