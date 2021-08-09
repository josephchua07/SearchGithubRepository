package com.chua.githubsearch.repository

import com.chua.githubsearch.domain.Item
import com.chua.githubsearch.network.ItemDto
import com.chua.githubsearch.network.Response
import com.chua.githubsearch.service.GithubService
import com.chua.githubsearch.util.DomainMapper

class GithubRepositoryImpl(
    private val service: GithubService,
    private val itemMapper: DomainMapper<ItemDto, Item>
) : GithubRepository {

    override suspend fun search(string: String, page: Int): Response<List<Item>> {
        return try {
            Response.Success(
                itemMapper.toListOfDomain(
                    service.search(string = string, page = page).items
                )
            )
        } catch (throwable: Throwable) {
            Response.GenericError(throwable.message ?: "Generic Error")
        }
    }

}