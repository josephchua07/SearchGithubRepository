package com.chua.githubsearch.repository

import com.chua.githubsearch.model.Item
import com.chua.githubsearch.model.Response

interface GithubRepository {

    suspend fun search(string: String): Response<List<Item>>

}