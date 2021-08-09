package com.chua.githubsearch.network

data class SearchResponse(
    val total_count: Int,
    val items: List<ItemDto>
)
