package com.rafaelmfer.githubrepo.presentation

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rafaelmfer.githubrepo.data.repository.State
import com.rafaelmfer.githubrepo.domain.model.GitHubRepositoriesModel
import com.rafaelmfer.githubrepo.domain.repository.IGitHubRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: IGitHubRepository
) : ViewModel() {

    companion object {
        private const val QUERY = "language:kotlin"
        private const val SORT = "stars"
    }

    private var page = 1

    private val reposMutableLiveData = MutableLiveData<State<GitHubRepositoriesModel>>()
    val reposLiveData: LiveData<State<GitHubRepositoriesModel>> get() = reposMutableLiveData

    private val loadMoreMutableLiveData = MutableLiveData<State<GitHubRepositoriesModel>>()
    val loadMoreLiveData: LiveData<State<GitHubRepositoriesModel>> get() = loadMoreMutableLiveData

    init {
        getRepos()
    }

    fun incrementPage(): Int {
        return page++
    }

    fun decrementPage(): Int {
        return page--
    }

    @VisibleForTesting
    fun getRepos() {
        reposMutableLiveData.postValue(State.Loading)
        viewModelScope.launch {
            reposMutableLiveData.postValue(repository.getRepos(QUERY, SORT, page))
        }
    }

    fun loadMoreRepos() {
        viewModelScope.launch {
            loadMoreMutableLiveData.postValue(repository.getRepos(QUERY, SORT, page))
        }
    }
}