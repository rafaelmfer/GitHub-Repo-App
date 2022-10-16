package com.rafaelmfer.githubrepo.data

import com.rafaelmfer.githubrepo.TestHelper
import com.rafaelmfer.githubrepo.data.remote.response.GitHubRepositoriesResponse
import com.rafaelmfer.githubrepo.data.repository.State
import com.rafaelmfer.githubrepo.domain.model.GitHubRepositoriesModel
import com.rafaelmfer.githubrepo.domain.repository.IGitHubRepository
import kotlin.reflect.KClass

// Use FakeDataSource that acts as a test double to the LocalDataSource
class FakeGitHubRepository : IGitHubRepository {

    companion object {
        const val GIT_HUB_REPOS_NOT_FOUND = "Repositories could not found"
        val gitHubReposResponse = readJson("git_repos_response_mock.json", GitHubRepositoriesResponse::class)
        val gitHubReposModel = readJson("git_repos_model_mock.json", GitHubRepositoriesModel::class)

        private fun <T : Any> readJson(fileName: String, clazz: KClass<T>): T {
            val jsonString = TestHelper.loadJsonAsString(fileName)
            return TestHelper.convertJsonToModel(jsonString, clazz.java)
        }
    }

    private var shouldReturnError = false

    fun shouldReturnError(value: Boolean) {
        shouldReturnError = value
    }

    override suspend fun getRepos(query: String, sort: String, page: Int): State<GitHubRepositoriesModel> {
        if (shouldReturnError) {
            return State.Error(GIT_HUB_REPOS_NOT_FOUND)
        }

        return State.Success(gitHubReposModel)
    }
}