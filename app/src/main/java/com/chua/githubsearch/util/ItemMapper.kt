package com.chua.githubsearch.util

import com.chua.githubsearch.domain.Item
import com.chua.githubsearch.network.ItemDto

class ItemMapper : DomainMapper<ItemDto, Item> {
    override fun toDomain(data: ItemDto) = Item(
        data.full_name,
        data.description,
        data.stargazers_count,
        data.updated_at
    )

    override fun toListOfDomain(dataList: List<ItemDto>) = dataList.map { toDomain(it) }
}