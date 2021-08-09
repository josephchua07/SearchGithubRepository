package com.chua.githubsearch.repository

import com.chua.githubsearch.domain.Item
import com.chua.githubsearch.network.Response

interface GithubRepository {

    suspend fun search(string: String, page: Int): Response<List<Item>>

}