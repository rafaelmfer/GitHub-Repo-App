package com.rafaelmfer.githubrepo.di

import com.rafaelmfer.githubrepo.data.repository.GitHubRepository
import com.rafaelmfer.githubrepo.domain.repository.IGitHubRepository
import org.koin.dsl.module

object RepositoryModule {
    val module = module {
        factory<IGitHubRepository> { GitHubRepository(get()) }
    }
}