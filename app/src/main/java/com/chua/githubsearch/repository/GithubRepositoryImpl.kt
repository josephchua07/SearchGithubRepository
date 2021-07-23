package com.chua.githubsearch.repository

import com.chua.githubsearch.model.Item
import com.chua.githubsearch.model.Response
import com.chua.githubsearch.service.GithubService

class GithubRepositoryImpl(private val service: GithubService) : GithubRepository {

    override suspend fun search(string: String, page: Int): Response<List<Item>> {
        return try {
            Response.Success(service.search(string = string, page = page).items)
        } catch (throwable: Throwable) {
            Response.GenericError("Generic Error")
        }
    }

}