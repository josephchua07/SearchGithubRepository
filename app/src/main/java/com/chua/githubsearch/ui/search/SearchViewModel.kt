package com.chua.githubsearch.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun search(string: String) {
        viewModelScope.launch {
            _status.postValue(Status.Loading)
            when (val response = githubRepository.search(string)) {
                is Response.Success -> {
                    _status.postValue(Status.Success(response.data))
                }
                is Response.GenericError -> {
                    _status.postValue(Status.Error(response.message))
                }
            }

        }
    }

}