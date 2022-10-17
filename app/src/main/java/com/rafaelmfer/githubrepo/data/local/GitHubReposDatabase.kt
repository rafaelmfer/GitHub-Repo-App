package com.rafaelmfer.githubrepo.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * The Room Database that contains the GitHub Repos table.
 */
@Database(entities = [GitHubRepositoriesEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class GitHubReposDatabase : RoomDatabase() {

    abstract fun gitHubReposDao(): GitHubReposDao
}