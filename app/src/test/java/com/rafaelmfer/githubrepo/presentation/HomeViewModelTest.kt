package com.rafaelmfer.githubrepo.presentation

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.rafaelmfer.githubrepo.MainCoroutineRule
import com.rafaelmfer.githubrepo.data.FakeGitHubRepository
import com.rafaelmfer.githubrepo.data.repository.State
import com.rafaelmfer.githubrepo.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.pauseDispatcher
import kotlinx.coroutines.test.resumeDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers.`is`
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class HomeViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var gitHubRepository: FakeGitHubRepository

    // Class under test
    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setUp() {
        gitHubRepository = FakeGitHubRepository()
        homeViewModel = HomeViewModel(gitHubRepository)
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `get repos _ show loading and then success`() {
        mainCoroutineRule.runBlockingTest {
            //WHEN
            mainCoroutineRule.pauseDispatcher()
            homeViewModel.getRepos()

            //THEN
            MatcherAssert.assertThat(homeViewModel.reposLiveData.getOrAwaitValue(), `is`(State.Loading))
            mainCoroutineRule.resumeDispatcher()
            MatcherAssert.assertThat(homeViewModel.reposLiveData.value, `is`(State.Success(FakeGitHubRepository.gitHubReposModel)))
        }
    }

    @Test
    fun `get repos _ show loading and then error`() {
        mainCoroutineRule.runBlockingTest {
            //GIVEN
            gitHubRepository.shouldReturnError(true)

            //WHEN
            mainCoroutineRule.pauseDispatcher()
            homeViewModel.getRepos()

            //THEN
            MatcherAssert.assertThat(homeViewModel.reposLiveData.getOrAwaitValue(), `is`(State.Loading))
            mainCoroutineRule.resumeDispatcher()
            MatcherAssert.assertThat(homeViewModel.reposLiveData.value, `is`(State.Error(FakeGitHubRepository.GIT_HUB_REPOS_NOT_FOUND)))
        }
    }

    @Test
    fun `load more repos _ show success`() {
        mainCoroutineRule.runBlockingTest {
            //WHEN
            homeViewModel.loadMoreRepos()

            //THEN
            MatcherAssert.assertThat(homeViewModel.reposLiveData.value, `is`(State.Success(FakeGitHubRepository.gitHubReposModel)))
        }
    }

    @Test
    fun `load more repos _ show error`() {
        mainCoroutineRule.runBlockingTest {
            //GIVEN
            gitHubRepository.shouldReturnError(true)

            //WHEN
            homeViewModel.loadMoreRepos()

            //THEN
            MatcherAssert.assertThat(homeViewModel.loadMoreLiveData.value, `is`(State.Error(FakeGitHubRepository.GIT_HUB_REPOS_NOT_FOUND)))
        }
    }
}