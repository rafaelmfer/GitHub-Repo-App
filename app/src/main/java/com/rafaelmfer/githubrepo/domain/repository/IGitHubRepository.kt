package com.rafaelmfer.githubrepo.domain.repository

import com.rafaelmfer.githubrepo.data.local.GitHubRepositoriesEntity
import com.rafaelmfer.githubrepo.data.remote.response.GitHubRepositoriesResponse
import com.rafaelmfer.githubrepo.data.remote.response.ItemResponse
import com.rafaelmfer.githubrepo.data.repository.State
import com.rafaelmfer.githubrepo.domain.model.GitHubRepositoriesModel

interface IGitHubRepository {
    suspend fun getRepos(query: String, sort: String, page: Int): State<GitHubRepositoriesModel>
    suspend fun getRepos(): GitHubRepositoriesEntity?
    suspend fun saveGitHubReposInfo(infoResponse: GitHubRepositoriesResponse)
    suspend fun saveMoreReposInsideList(reposInfo: GitHubRepositoriesEntity, list: List<ItemResponse>)
    suspend fun deleteGitHubReposInfo()
}