package com.rafaelmfer.githubrepo.domain

import com.rafaelmfer.githubrepo.data.local.GitHubRepositoriesEntity
import com.rafaelmfer.githubrepo.data.remote.response.GitHubRepositoriesResponse
import com.rafaelmfer.githubrepo.data.remote.response.ItemResponse
import com.rafaelmfer.githubrepo.domain.model.GitHubRepositoriesModel
import com.rafaelmfer.githubrepo.domain.model.ItemModel

fun GitHubRepositoriesResponse.asDomainModel(): GitHubRepositoriesModel {
    return GitHubRepositoriesModel(
        totalCount = totalCount,
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

fun GitHubRepositoriesResponse.asDatabaseEntity(): GitHubRepositoriesEntity {
    return GitHubRepositoriesEntity(
        totalCount = totalCount,
        items = items.mapItemResponseToEntity().toMutableList()
    )
}

fun List<ItemResponse>.mapItemResponseToEntity() = map { item ->
    GitHubRepositoriesEntity.ItemEntity(
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

fun GitHubRepositoriesEntity.asDomainModel(): GitHubRepositoriesModel {
    return GitHubRepositoriesModel(
        totalCount = totalCount,
        items = items.map { item ->
            ItemModel(
                id = item.id,
                repoName = item.repoName,
                userName = item.userName,
                userPhoto = item.userPhoto,
                htmlUrl = item.htmlUrl,
                description = item.description,
                stargazersCount = item.stargazersCount,
                forksCount = item.forksCount
            )
        }
    )
}