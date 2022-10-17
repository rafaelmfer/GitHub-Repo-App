package com.rafaelmfer.githubrepo.presentation

import android.app.Application
import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.rafaelmfer.githubrepo.R
import com.rafaelmfer.githubrepo.data.local.LocalDB
import com.rafaelmfer.githubrepo.data.network.HTTPClient
import com.rafaelmfer.githubrepo.data.remote.api.IGitHubApi
import com.rafaelmfer.githubrepo.data.repository.GitHubRepository
import com.rafaelmfer.githubrepo.domain.repository.IGitHubRepository
import com.rafaelmfer.githubrepo.util.RecyclerViewMatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get

@RunWith(AndroidJUnit4::class)
@LargeTest
//END TO END test to black box test the app
class HomeActivityTest : KoinTest {

    private lateinit var repository: IGitHubRepository
    private lateinit var viewModel: HomeViewModel
    private lateinit var appContext: Application

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(HomeActivity::class.java)
    private lateinit var decorView: View

    /**
     * As we use Koin as a Service Locator Library to develop our code, we'll also use Koin to test our code.
     * at this step we will initialize Koin related code to be able to use it in out testing.
     */
    @Before
    fun init() {
        stopKoin()//stop the original app koin
        appContext = ApplicationProvider.getApplicationContext()
        val myModule = module {
            single { HTTPClient().create(IGitHubApi::class, IGitHubApi.BASE_URL) }
            single { LocalDB.createGitHubReposDao(appContext) }
            single<IGitHubRepository> { GitHubRepository(get(), get()) }
            viewModel { HomeViewModel(get()) }
        }
        //declare a new koin module
        startKoin {
            modules(listOf(myModule))
        }
        //Get our real repository
        repository = get()
        viewModel = get()

        activityScenarioRule.scenario.onActivity { activity ->
            decorView = activity.window.decorView
        }

        Thread.sleep(3000L)
    }

    @Test
    fun checkItemWhenScroll() {
        ActivityScenario.launch(HomeActivity::class.java)
        waitViewAppear(onView(withId(R.id.rv_repos)))

        RecyclerViewMatcher.scrollToPosition(R.id.rv_repos, 5)
        RecyclerViewMatcher.hasViewInPosition(R.id.rv_repos, 5)

        RecyclerViewMatcher.scrollToPosition(R.id.rv_repos, 2)
        RecyclerViewMatcher.hasViewInPosition(R.id.rv_repos, 2)

    }

    private fun waitViewAppear(viewInteraction: ViewInteraction) {
        var i = 500
        var throwable: Throwable? = null
        while (i > 0) {
            try {
                viewInteraction.check(matches(isDisplayed()))
                break
            } catch (t: Throwable) {
                i--
                throwable = t
                Thread.sleep(10)
            }
        }
        if (i == 0) {
            throw throwable!!
        }
    }
}