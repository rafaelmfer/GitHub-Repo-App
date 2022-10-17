package com.rafaelmfer.githubrepo.data.repository

import com.rafaelmfer.githubrepo.data.remote.api.IGitHubApi
import com.rafaelmfer.githubrepo.domain.asDomainModel
import com.rafaelmfer.githubrepo.domain.model.GitHubRepositoriesModel
import com.rafaelmfer.githubrepo.domain.repository.IGitHubRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * A sealed class that encapsulates successful outcome with a value of type [T]
 * or a failure with message and statusCode
 */
sealed class State<out T> {
    object Loading : State<Nothing>()
    data class Success<out T>(val model: T) : State<T>()
    data class Error(val message: String?) : State<Nothing>()
}

class GitHubRepository(
    private val iGitHubApi: IGitHubApi
) : IGitHubRepository {

    override suspend fun getRepos(query: String, sort: String, page: Int): State<GitHubRepositoriesModel> {
        return withContext(Dispatchers.IO) {
            try {
                val response = iGitHubApi.getRepos(query = query, sort = sort, page = page)
                val model = response.asDomainModel()
                State.Success(model)
            } catch (ex: Exception) {
                State.Error(message = ex.localizedMessage)
            }
        }
    }
}