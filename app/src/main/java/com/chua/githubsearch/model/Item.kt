package com.chua.githubsearch.model

data class Item(
    val full_name: String,
    val description: String,
    val stargazers_count: Int,
    val updated_at: String
)
