package com.rafaelmfer.githubrepo.data.local

import android.content.Context
import androidx.room.Room

/**
 * Singleton class that is used to create a reminder db
 */
object LocalDB {

    /**
     * static method that creates a reminder class and returns the DAO of the reminder
     */
    fun createGitHubReposDao(context: Context): GitHubReposDao {
        return Room.databaseBuilder(
            context.applicationContext,
            GitHubReposDatabase::class.java, "GitHubRepos.db"
        )
            .build()
            .gitHubReposDao()
    }

}