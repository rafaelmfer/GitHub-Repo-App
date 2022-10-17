package com.rafaelmfer.githubrepo.data.remote.api

import com.rafaelmfer.githubrepo.data.remote.response.GitHubRepositoriesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface IGitHubApi {

    companion object {
        const val BASE_URL = "https://api.github.com/"
    }

    @GET("search/repositories")
    suspend fun getRepos(
        @Query("q") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int
    ): GitHubRepositoriesResponse
}