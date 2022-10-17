package com.rafaelmfer.githubrepo.util

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Description
import org.hamcrest.Matcher

object RecyclerViewMatcher {

    fun scrollToPosition(resId: Int, position: Int): ViewAction =
        RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
            position
        ).also {
            Espresso.onView(ViewMatchers.withId(resId)).perform(it)
        }

    fun clickOnPosition(resId: Int, position: Int): ViewAction =
        RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
            position,
            ViewActions.click()
        ).also {
            Espresso.onView(ViewMatchers.withId(resId)).perform(it)
        }

    fun hasViewInPosition(resId: Int, position: Int) {
        val view = withViewAtPosition(
            position,
            ViewMatchers.hasDescendant(ViewMatchers.isDisplayed())
        )
        val assertion = ViewAssertions.matches(view)
        Espresso.onView(ViewMatchers.withId(resId)).check(assertion)
    }

    private fun withViewAtPosition(position: Int, itemMatcher: Matcher<View>): Matcher<View> {
        return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                itemMatcher.describeTo(description)
            }

            override fun matchesSafely(recyclerView: RecyclerView): Boolean {
                val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
                return viewHolder != null && itemMatcher.matches(viewHolder.itemView)
            }
        }
    }
}