package com.udafil.dhruvamsharma.bakingandroidapp;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.udafil.dhruvamsharma.bakingandroidapp.main.MainActivity;
import com.udafil.dhruvamsharma.bakingandroidapp.recipeDetail.RecipeDetail;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.anything;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class NextRecipeButtonTest {

    @Rule public ActivityTestRule<RecipeDetail> recipeDetailFragmentActivityTestRule =
            new ActivityTestRule<>(RecipeDetail.class);

    @Test
    public void checkForDataInAdapter() {

        //find the adapter view and perform a click action
        onData(anything()).inAdapterView(withId(R.id.main_recipe_list_rv)).atPosition(0).perform(click());
        //onView(withId(R.id.change_btn)).perform(click());

        //check if the data is same as expected
        //onView(withId(R.id.recipe_heading_tv)).check(matches(withText("Nutella Pie")));

    }

}
