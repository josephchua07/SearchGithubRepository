package com.chua.githubsearch.repository

import com.chua.githubsearch.model.Item

interface GithubRepository {

    suspend fun search(string: String): List<Item>

}