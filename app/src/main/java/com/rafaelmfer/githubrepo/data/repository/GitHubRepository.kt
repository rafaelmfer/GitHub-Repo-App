package com.rafaelmfer.githubrepo.data.repository

import com.rafaelmfer.githubrepo.data.local.GitHubReposDao
import com.rafaelmfer.githubrepo.data.local.GitHubRepositoriesEntity
import com.rafaelmfer.githubrepo.data.remote.api.IGitHubApi
import com.rafaelmfer.githubrepo.data.remote.response.GitHubRepositoriesResponse
import com.rafaelmfer.githubrepo.data.remote.response.ItemResponse
import com.rafaelmfer.githubrepo.domain.asDatabaseEntity
import com.rafaelmfer.githubrepo.domain.asDomainModel
import com.rafaelmfer.githubrepo.domain.mapItemResponseToEntity
import com.rafaelmfer.githubrepo.domain.model.GitHubRepositoriesModel
import com.rafaelmfer.githubrepo.domain.repository.IGitHubRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

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
    private val iGitHubApi: IGitHubApi,
    private val gitHubReposDao: GitHubReposDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : IGitHubRepository {

    override suspend fun getRepos(query: String, sort: String, page: Int): State<GitHubRepositoriesModel> {
        return withContext(ioDispatcher) {
            try {
                val response = iGitHubApi.getRepos(query = query, sort = sort, page = page)
                State.Success(response.asDomainModel())
            } catch (ex: IOException) {
                // Network Error
                State.Error(message = ex.localizedMessage)
            } catch (ex: Exception) {
                State.Error(message = ex.localizedMessage)
            }
        }
    }

    override suspend fun getRepos(): GitHubRepositoriesEntity? {
        return withContext(ioDispatcher) {
            gitHubReposDao.getRepos()
        }
    }

    override suspend fun saveGitHubReposInfo(infoResponse: GitHubRepositoriesResponse) {
        withContext(ioDispatcher) {
            gitHubReposDao.saveRepos(infoResponse.asDatabaseEntity())
        }
    }

    override suspend fun saveMoreReposInsideList(reposInfo: GitHubRepositoriesEntity, list: List<ItemResponse>) {
        withContext(ioDispatcher) {
            val repos = list.mapItemResponseToEntity()
            reposInfo.items.addAll(repos)
            gitHubReposDao.saveRepos(reposInfo)
        }
    }

    override suspend fun deleteGitHubReposInfo() {
        withContext(ioDispatcher) {
            gitHubReposDao.deleteAll()
        }
    }
}