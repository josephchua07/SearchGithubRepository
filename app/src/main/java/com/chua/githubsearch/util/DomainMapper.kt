package com.chua.githubsearch.util

interface DomainMapper<T, Domain> {

    fun toDomain(data: T): Domain

    fun toListOfDomain(dataList: List<T>): List<Domain>

}