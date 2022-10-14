package com.rafaelmfer.githubrepo.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rafaelmfer.githubrepo.data.repository.State
import com.rafaelmfer.githubrepo.databinding.ActivityHomeBinding
import com.rafaelmfer.githubrepo.domain.model.GitHubRepositoriesModel
import com.rafaelmfer.githubrepo.extensions.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private val viewModel by viewModel<HomeViewModel>()
    private val binding by viewBinding(ActivityHomeBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.onViewCreated()
    }

    private fun ActivityHomeBinding.onViewCreated() {
        observables()
    }

    private fun ActivityHomeBinding.observables() {
        viewModel.reposLiveData.observe(this@HomeActivity) {
            handlerReposState(it)
        }
    }

    private fun ActivityHomeBinding.handlerReposState(state: State<GitHubRepositoriesModel>) {
        when (state) {
            is State.Loading -> {
            }
            is State.Success -> {
            }
            is State.Error -> {
            }
        }
    }
}