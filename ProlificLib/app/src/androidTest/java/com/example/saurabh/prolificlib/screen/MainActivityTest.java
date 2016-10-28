package com.example.saurabh.prolificlib.screen;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.example.saurabh.prolificlib.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by saurabh on 10/27/16.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityTest
{
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);


    @Test
    public void testShouldShowRecyclerViewAndCheckOnClick()
    {
        onView(ViewMatchers.withId((R.id.booklistview)))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));

        onView(withId(R.id.booklistview)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.selecedtitle)).check(matches(isDisplayed()));


    }



}