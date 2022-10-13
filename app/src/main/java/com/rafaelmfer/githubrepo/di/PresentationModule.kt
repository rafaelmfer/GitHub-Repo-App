package com.rafaelmfer.githubrepo.di

import com.rafaelmfer.githubrepo.presentation.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object PresentationModule {
    val module = module {
        viewModel { HomeViewModel(get()) }
    }
}