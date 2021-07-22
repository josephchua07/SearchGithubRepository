package com.chua.githubsearch.model

sealed interface Status {
    data class Success(val data: List<Item>) : Status
    data class Error(val message: String) : Status
    object Loading : Status
}