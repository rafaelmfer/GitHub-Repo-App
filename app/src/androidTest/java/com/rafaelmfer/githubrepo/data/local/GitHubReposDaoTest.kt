package com.rafaelmfer.githubrepo.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.hamcrest.Matchers.`is`
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
//Unit test the DAO
@SmallTest
class GitHubReposDaoTest {

    private val gitHubRepositoriesEntity = GitHubRepositoriesEntity(
        totalCount = 15648,
        items = mutableListOf(
            GitHubRepositoriesEntity.ItemEntity(
                id = 1,
                repoName = "Coroutines",
                userName = "Androidx",
                userPhoto = "https://www.123.com.br/photo",
                htmlUrl = "https://www.123.com.br/photo",
                description = null,
                stargazersCount = 1654,
                forksCount = 1564
            )
        )
    )
    private lateinit var database: GitHubReposDatabase

    @Before
    fun createDb() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            GitHubReposDatabase::class.java
        ).build()
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun saveReposInfoOnDb_getReposInfoFromDb() = runBlocking {
        database.gitHubReposDao().saveRepos(gitHubRepositoriesEntity)

        val reposEntity = database.gitHubReposDao().getRepos()

        MatcherAssert.assertThat(reposEntity as GitHubRepositoriesEntity, Matchers.notNullValue())
        MatcherAssert.assertThat(reposEntity.totalCount, `is`(gitHubRepositoriesEntity.totalCount))
        MatcherAssert.assertThat(reposEntity.items.size, `is`(gitHubRepositoriesEntity.items.size))
        MatcherAssert.assertThat(reposEntity.items[0], `is`(gitHubRepositoriesEntity.items[0]))
    }

    @Test
    fun saveReposInfoOnDb_getThisReposInfo_ThenDeleteAll() = runBlocking {
        database.gitHubReposDao().saveRepos(gitHubRepositoriesEntity)

        val repos = database.gitHubReposDao().getRepos()

        database.gitHubReposDao().deleteAll()

        val reposUpdated = database.gitHubReposDao().getRepos()

        MatcherAssert.assertThat(repos, Matchers.notNullValue())
        MatcherAssert.assertThat(repos?.items?.size, `is`(1))

        MatcherAssert.assertThat(reposUpdated, Matchers.nullValue())
    }
}