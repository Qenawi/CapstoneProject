package com.example.qenawi.ttasker_capstone;


import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    private IdlingResource idlingResource;

    @Before
    public void Reg() {
        idlingResource = mActivityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(idlingResource);
    }

    @Test
    public void mainActivityTest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        ViewInteraction qf = onView(
                allOf(withText("create"),
                        withParent(allOf(withId(R.id.creat),
                                withParent(withId(R.id.fragment)))
                        ),
                        isDisplayed()));

    }

    public void UnReg() {
        Espresso.unregisterIdlingResources(idlingResource);
    }

}
