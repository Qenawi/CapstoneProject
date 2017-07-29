package com.example.qenawi.ttasker_capstone;


import android.content.Intent;
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
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class myprojects
{

    @Rule
    public ActivityTestRule<MainActivity3TasksHandler> mActivityTestRule = new ActivityTestRule<>(MainActivity3TasksHandler.class,true,false);
    private IdlingResource idlingResource;
    @Before
    public void Reg()
    {
        Intent i=new Intent();
        i.putExtra("target","3");
        mActivityTestRule.launchActivity(i);
        idlingResource=mActivityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(idlingResource);
    }
    @Test
    public void mainActivityTest()
    {
        ViewInteraction recyclerView = onView
                (
                        allOf(withId(R.id.project_list),
                                withParent(withId(R.id.fragment)),
                                isDisplayed()));

    }
    public void UnReg()
    {
        Espresso.unregisterIdlingResources(idlingResource);
    }

}
