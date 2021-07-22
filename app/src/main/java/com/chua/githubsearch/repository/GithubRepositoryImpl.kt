package com.chua.githubsearch.repository

import com.chua.githubsearch.model.Item
import com.chua.githubsearch.service.GithubService

class GithubRepositoryImpl(private val service: GithubService) : GithubRepository {

    override suspend fun search(string: String): List<Item> {
        return service.search(string).items
    }

}