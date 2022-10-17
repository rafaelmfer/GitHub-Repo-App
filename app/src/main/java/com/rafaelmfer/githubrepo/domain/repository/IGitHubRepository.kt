package com.rafaelmfer.githubrepo.domain.repository

import com.rafaelmfer.githubrepo.data.repository.State
import com.rafaelmfer.githubrepo.domain.model.GitHubRepositoriesModel

interface IGitHubRepository {
    suspend fun getRepos(query: String, sort: String, page: Int): State<GitHubRepositoriesModel>
}