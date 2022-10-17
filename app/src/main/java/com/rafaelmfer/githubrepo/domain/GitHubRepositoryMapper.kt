package com.rafaelmfer.githubrepo.domain

import com.rafaelmfer.githubrepo.data.remote.response.GitHubRepositoriesResponse
import com.rafaelmfer.githubrepo.domain.model.GitHubRepositoriesModel
import com.rafaelmfer.githubrepo.domain.model.ItemModel

fun GitHubRepositoriesResponse.asDomainModel(): GitHubRepositoriesModel {
    return GitHubRepositoriesModel(
        totalCount = totalCount,
        incompleteResults = incompleteResults,
        items = items.map { item ->
            ItemModel(
                id = item.id,
                repoName = item.name,
                userName = item.owner.login,
                userPhoto = item.owner.avatarUrl,
                htmlUrl = item.htmlUrl,
                description = item.description,
                stargazersCount = item.stargazersCount,
                forksCount = item.forksCount
            )
        }
    )
}