package com.rafaelmfer.githubrepo

import android.app.Application
import com.rafaelmfer.githubrepo.di.NetworkModule
import com.rafaelmfer.githubrepo.di.PresentationModule
import com.rafaelmfer.githubrepo.di.RepositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GitHubRepoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@GitHubRepoApplication)
            modules(
                NetworkModule.module,
                RepositoryModule.module,
                PresentationModule.module,
            )
        }
    }
}