package com.rafaelmfer.githubrepo.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Data Access Object for the repos table.
 */
@Dao
interface GitHubReposDao {
    /**
     * @return all jokes.
     */
    @Query("SELECT * FROM repos_table_name")
    suspend fun getRepos(): GitHubRepositoriesEntity?

    /**
     *Insert GitHub Repos Info in the database
     *
     * @param repos the repos to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveRepos(repos: GitHubRepositoriesEntity)

    /**
     * Delete all gitHub info saved in database.
     */
    @Query("DELETE FROM repos_table_name")
    suspend fun deleteAll()
}