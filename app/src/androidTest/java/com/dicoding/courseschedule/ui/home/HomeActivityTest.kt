package com.dicoding.courseschedule.ui.home

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.dicoding.courseschedule.R
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class HomeActivityTest {

    @Before
    fun setUp() {
        ActivityScenario.launch(HomeActivity::class.java)
    }

    @Test
    fun testTapAddCourse_openAddCourseActivity() {
        onView(withId(R.id.action_add)).perform(click())
        onView(withId(R.id.view_home)).check(doesNotExist())
        onView(withId(R.id.ed_course_name)).check(matches(isDisplayed()))
        onView(withId(R.id.spinner_day)).check(matches(isDisplayed()))
        onView(withId(R.id.ib_start_time)).check(matches(isDisplayed()))
        onView(withId(R.id.ib_end_time)).check(matches(isDisplayed()))
        onView(withId(R.id.ed_lecturer)).check(matches(isDisplayed()))
        onView(withId(R.id.ed_note)).check(matches(isDisplayed()))
        onView(withId(R.id.action_insert)).check(matches(isDisplayed()))
    }
}