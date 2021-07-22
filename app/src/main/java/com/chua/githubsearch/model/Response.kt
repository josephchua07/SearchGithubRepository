package com.chua.githubsearch.model

data class Response(
    val total_count: Int,
    val items: List<Item>
)
