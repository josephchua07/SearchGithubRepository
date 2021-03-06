package com.chua.githubsearch.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chua.githubsearch.domain.Item
import com.chua.githubsearch.network.Response
import com.chua.githubsearch.network.Status
import com.chua.githubsearch.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val githubRepository: GithubRepository
) : ViewModel() {

    private val _status = MutableLiveData<Status<Item>>()
    val status: LiveData<Status<Item>>
        get() = _status

    private var items = mutableListOf<Item>()
    private var searchString = ""
    private var page = 1

    fun search(
        searchString: String,
        page: Int = 1
    ) {
        this.searchString = searchString
        viewModelScope.launch {
            _status.postValue(Status.Loading)
            when (val response = githubRepository.search(searchString, page)) {
                is Response.Success -> {
                    items.addAll(response.data.toMutableList())
                    _status.postValue(Status.Success(items))
                }
                is Response.GenericError -> {
                    _status.postValue(Status.Error(response.message))
                }
            }

        }
    }

    fun reset() {
        page = 1
        items = mutableListOf()
    }

    fun loadMore() {
        search(searchString, ++page)
    }

}