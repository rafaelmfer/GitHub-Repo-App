package com.rafaelmfer.githubrepo.di

import com.rafaelmfer.githubrepo.data.network.HTTPClient
import com.rafaelmfer.githubrepo.data.remote.api.IGitHubApi
import org.koin.dsl.module

object NetworkModule {
    val module = module {
        single { HTTPClient() }
        factory { get<HTTPClient>().create(IGitHubApi::class, IGitHubApi.BASE_URL) }
    }
}