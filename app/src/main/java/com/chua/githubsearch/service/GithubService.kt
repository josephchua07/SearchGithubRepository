package com.chua.githubsearch.service

import com.chua.githubsearch.network.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {

    @GET("search/repositories")
    suspend fun search(
        @Query("q") string: String,
        @Query("per_page") per_page: Int = 10,
        @Query("page") page: Int
    ): SearchResponse

}