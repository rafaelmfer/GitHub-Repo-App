package com.rafaelmfer.githubrepo.di

import com.rafaelmfer.githubrepo.data.local.LocalDB
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object DatabaseModule {
    val module = module {
        single { LocalDB.createGitHubReposDao(androidContext()) }
    }
}