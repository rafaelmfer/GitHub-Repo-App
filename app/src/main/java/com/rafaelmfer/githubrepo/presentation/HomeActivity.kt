package com.rafaelmfer.githubrepo.presentation

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.rafaelmfer.githubrepo.R
import com.rafaelmfer.githubrepo.data.repository.State
import com.rafaelmfer.githubrepo.databinding.ActivityHomeBinding
import com.rafaelmfer.githubrepo.domain.model.GitHubRepositoriesModel
import com.rafaelmfer.githubrepo.extensions.gone
import com.rafaelmfer.githubrepo.extensions.viewBinding
import com.rafaelmfer.githubrepo.extensions.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private val viewModel by viewModel<HomeViewModel>()
    private val binding by viewBinding(ActivityHomeBinding::inflate)

    private val reposAdapter = ReposAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.onViewCreated()
    }

    private fun ActivityHomeBinding.onViewCreated() {
        observables()
        setupToolbar()
        setupRecycler()
    }

    private fun ActivityHomeBinding.observables() {
        viewModel.reposLiveData.observe(this@HomeActivity) {
            handlerReposState(it)
        }
    }

    private fun ActivityHomeBinding.setupToolbar() {
        mtbRepos.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.day_night -> {
                    when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
                        Configuration.UI_MODE_NIGHT_YES -> {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        }
                        Configuration.UI_MODE_NIGHT_NO -> {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        }
                    }
                    true
                }
                else -> false
            }
        }
    }

    private fun ActivityHomeBinding.setupRecycler() {
        rvRepos.apply {
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            adapter = reposAdapter
        }
    }

    private fun ActivityHomeBinding.handlerReposState(state: State<GitHubRepositoriesModel>) {
        when (state) {
            is State.Loading -> {
                pbRepos.visible
                rvRepos.gone
                tvReposError.gone
            }
            is State.Success -> {
                rvRepos.visible
                reposAdapter.updateRepoList(state.model.items)
                pbRepos.gone
                tvReposError.gone
            }
            is State.Error -> {
                pbRepos.gone
                rvRepos.gone
                tvReposError.apply {
                    visible
                    if (!state.message.isNullOrBlank()) text = state.message
                }
            }
        }
    }
}