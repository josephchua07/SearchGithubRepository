package com.chua.githubsearch.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chua.githubsearch.model.Item
import com.chua.githubsearch.model.Response
import com.chua.githubsearch.model.Status
import com.chua.githubsearch.repository.GithubRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val githubRepository: GithubRepository
) : ViewModel() {

    private val _status = MutableLiveData<Status>()
    val status: LiveData<Status>
        get() = _status

    private var items = mutableListOf<Item>()
    private var searchString = ""
    private var page = 1

    fun search(
        searchString: String,
        page: Int = 1,
        successAction: (List<Item>) -> Unit = { items ->
            _status.postValue(Status.Success(items))
        }
    ) {
        this.searchString = searchString
        viewModelScope.launch {
            _status.postValue(Status.Loading)
            when (val response = githubRepository.search(searchString, page)) {
                is Response.Success -> {
                    items.addAll(response.data.toMutableList())
                    successAction(items)
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