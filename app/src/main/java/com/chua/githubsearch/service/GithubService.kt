package com.chua.githubsearch.service

import com.chua.githubsearch.model.SearchResult
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {

    @GET("search/repositories")
    suspend fun search(@Query("q") string: String): SearchResult

}