package com.ahmety.newsapp

import android.text.SpannableString
import android.text.style.UnderlineSpan
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.InstrumentationRegistry
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.runner.AndroidJUnit4
import com.ahmety.newsapp.extension.doUnderline
import com.ahmety.newsapp.view.news.NewsListFragment
import com.ahmety.poilabscase.R
import org.hamcrest.CoreMatchers.containsString

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.ahmety.newsapp", appContext.packageName)
    }

    @Test
    fun testProgressBarVisibilityOnLoading() {
        launchFragmentInContainer<NewsListFragment>()
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()))
    }

    @Test
    fun testEmptyStateVisibility() {
        launchFragmentInContainer<NewsListFragment>()
        onView(withId(R.id.layoutEmpty)).check(matches(isDisplayed()))
    }

    @Test
    fun testErrorStateVisibility() {
        launchFragmentInContainer<NewsListFragment>()
        onView(withId(R.id.layoutError)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewError)).check(matches(withText("An error occurred")))
    }

    @Test
    fun testSearchFunctionality() {
        launchFragmentInContainer<NewsListFragment>()
        onView(withId(R.id.etSearch)).perform(typeText("android"), closeSoftKeyboard())
        onView(withText(containsString("android"))).check(matches(isDisplayed()))
    }

}
