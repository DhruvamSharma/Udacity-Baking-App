package com.udafil.dhruvamsharma.bakingandroidapp.main;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.widget.Toast;

import com.squareup.leakcanary.LeakCanary;
import com.udafil.dhruvamsharma.bakingandroidapp.R;
import com.udafil.dhruvamsharma.bakingandroidapp.data.model.RecipeModel;
import com.udafil.dhruvamsharma.bakingandroidapp.databinding.ActivityMainBinding;
import com.udafil.dhruvamsharma.bakingandroidapp.main.IdlingResource.RecipeIdlingResource;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MessageDelayer.DelayerCallback{

    private MainActivityViewModel viewModel;
    private RecipeListAdapter mAdapter;
    private ActivityMainBinding binding;

    // The Idling Resource which will be null in production.
    @Nullable private RecipeIdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        /*
        //LeakCanary setup to see the leaks in the application
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(getApplication());*/


        //Viewmodel setup for the MainActivity
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        viewModel.init();

        //setting up the views in the activity
        setupActivity();

        //Setting up animations
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setupWindowAnimations();
        }

    }



    /**
     * MainActivity setup method that displays data on the views
     */
    private void setupActivity() {

        MessageDelayer.processMessage("hi", this, mIdlingResource, this);

        //Getting data from the view model
        List<RecipeModel> recipeData = viewModel.getRecipeData();

        //Setting up the recycler view
        mAdapter = new RecipeListAdapter(recipeData, this);

        binding.mainRecipeListRv.setAdapter(mAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        binding.mainRecipeListRv.setLayoutManager(manager);


    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setupWindowAnimations() {
        Slide slide = new Slide();
        slide.setDuration(1000);
        getWindow().setExitTransition(slide);


        getWindow().setReenterTransition(slide);
    }

    @Override
    public void onDone(List<RecipeModel> recipeModel) {

        Toast.makeText(this, "done",Toast.LENGTH_SHORT).show();

    }

    /**
     * Only called from test, creates and returns a new {@link RecipeIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new RecipeIdlingResource();
        }
        return mIdlingResource;
    }
}
