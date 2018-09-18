package com.udafil.dhruvamsharma.bakingandroidapp;


import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.udafil.dhruvamsharma.bakingandroidapp.data.model.RecipeModel;
import com.udafil.dhruvamsharma.bakingandroidapp.main.MainActivity;
import com.udafil.dhruvamsharma.bakingandroidapp.recipeDetail.RecipeDetail;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import com.udafil.dhruvamsharma.bakingandroidapp.main.IdlingResource.RecipeIdlingResource;
import com.udafil.dhruvamsharma.bakingandroidapp.main.MainActivity;
import com.udafil.dhruvamsharma.bakingandroidapp.recipeDetail.RecipeDetail;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class NameOfRecipeTest {

    @Rule public ActivityTestRule<MainActivity> recipeDetailActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    private IdlingResource mRecipeIdlingResource;

    private String mRecipeName = "Yellow Cake";

    @Before
    public void registerIdlingResource() {
        mRecipeIdlingResource = recipeDetailActivityTestRule.getActivity().getIdlingResource();

        Espresso.registerIdlingResources(mRecipeIdlingResource);
    }

    @Test
    public void checkForName_afterRecyclerViewClick() {

        onView(withId(R.id.main_recipe_list_rv)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));

        onView(withId(R.id.recipe_heading_tv)).check(matches(withText(mRecipeName)));

    }

    @After
    public void unregisterIdlingResource() {
        Espresso.unregisterIdlingResources(mRecipeIdlingResource);
    }

}
