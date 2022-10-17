package com.rafaelmfer.githubrepo.domain.model

data class GitHubRepositoriesModel(
    val totalCount: Int,
    val items: List<ItemModel>
)

data class ItemModel(
    val id: Int,
    val repoName: String,
    val userName: String,
    val userPhoto: String,
    val htmlUrl: String,
    val description: String?,
    val stargazersCount: Int,
    val forksCount: Int
)