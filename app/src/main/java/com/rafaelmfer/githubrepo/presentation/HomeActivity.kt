package com.rafaelmfer.githubrepo.presentation

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    private var shouldIncrementPage = true
    private var totalResults = 1

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
        viewModel.run {
            reposLiveData.observe(this@HomeActivity) {
                handlerReposState(it)
            }

            loadMoreLiveData.observe(this@HomeActivity) {
                handlerLoadMoreReposState(it)
            }
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
            adapter = reposAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val forthLastView = layoutManager?.findViewByPosition(reposAdapter.itemCount - 4)?.let {
                        layoutManager?.isViewPartiallyVisible(it, false, true)
                    }
                    if (reposAdapter.itemCount <= totalResults && shouldIncrementPage && (forthLastView == true)) {
                        viewModel.incrementPage()
                        viewModel.loadMoreRepos()
                        shouldIncrementPage = false
                    }
                }
            })
        }
    }

    private fun ActivityHomeBinding.handlerReposState(state: State<GitHubRepositoriesModel>) {
        when (state) {
            is State.Loading -> {
                pbRepos.visible
                tvReposError.gone
            }
            is State.Success -> {
                pbRepos.gone
                reposAdapter.addMoreItems(state.model.items)
                totalResults = state.model.totalCount
                tvReposError.gone
            }
            is State.Error -> {
                pbRepos.gone
                tvReposError.apply {
                    visible
                    if (!state.message.isNullOrBlank()) text = state.message
                }
            }
        }
    }

    private fun handlerLoadMoreReposState(state: State<GitHubRepositoriesModel>) {
        when (state) {
            is State.Success -> {
                reposAdapter.addMoreItems(state.model.items)
                totalResults = state.model.totalCount
                shouldIncrementPage = true
            }
            is State.Error -> {
                viewModel.decrementPage()
                shouldIncrementPage = true
                Toast.makeText(this@HomeActivity, "Error getting more repos", Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }
}