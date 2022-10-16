package com.rafaelmfer.githubrepo.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Immutable model class for a GitHubRepos. In order to compile with Room
 *
 * @param totalCount            quantity of repos in the server
 */
@Entity(tableName = "repos_table_name")
data class GitHubRepositoriesEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id_repo") val idRepo: Int = 0,
    @ColumnInfo(name = "totalCount") val totalCount: Int,
    val items: MutableList<ItemEntity>
) {

    /**
     * @param id                    repo id
     * @param repoName              repo's name
     * @param userName              user's name of the repo
     * @param userPhoto             user's photo
     * @param htmlUrl               url of the repo
     * @param description           description of the repo
     * @param stargazersCount       repo's stars quantity
     * @param forksCount            repo's forks quantity
     */
    data class ItemEntity(
        @ColumnInfo(name = "id") val id: Int,
        @ColumnInfo(name = "repoName") val repoName: String,
        @ColumnInfo(name = "userName") val userName: String,
        @ColumnInfo(name = "userPhoto") val userPhoto: String,
        @ColumnInfo(name = "htmlUrl") val htmlUrl: String,
        @ColumnInfo(name = "description") val description: String?,
        @ColumnInfo(name = "stargazersCount") val stargazersCount: Int,
        @ColumnInfo(name = "forksCount") val forksCount: Int
    )
}


