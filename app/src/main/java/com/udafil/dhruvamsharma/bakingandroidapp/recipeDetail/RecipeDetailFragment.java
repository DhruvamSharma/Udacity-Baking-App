package com.udafil.dhruvamsharma.bakingandroidapp.recipeDetail;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.udafil.dhruvamsharma.bakingandroidapp.R;
import com.udafil.dhruvamsharma.bakingandroidapp.data.model.RecipeModel;
import com.udafil.dhruvamsharma.bakingandroidapp.data.model.Step;

import org.parceler.Parcels;

import java.util.Objects;

import ernestoyaquello.com.verticalstepperform.VerticalStepperFormLayout;
import ernestoyaquello.com.verticalstepperform.interfaces.VerticalStepperForm;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecipeDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class RecipeDetailFragment extends Fragment implements VerticalStepperForm{

    private OnFragmentInteractionListener mListener;
    //private RecyclerView mRecipeSteps;
    //private RecipeDetailAdapter mAdapter;
    private RecipeModel recipeData;
    private ImageView noDetails;

    private VerticalStepperFormLayout verticalStepperForm;
    private String[] mySteps;

    public RecipeDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        setupFragment(view);

        return view;
    }

    private void setupFragment(View view) {

        //noDetails = view.findViewById(R.id.no_food_iv);
        //mRecipeSteps = view.findViewById(R.id.recipe_detail_fragment_steps_rv);

        Bundle bundle;

        if (getArguments() != null) {

            bundle = getArguments();

            recipeData = Parcels.unwrap(bundle.getParcelable(getContext().getPackageName()));

            mySteps = new String[recipeData.getSteps().size()];

            int k = 0;

            //getting primary colors for stepper
            int colorPrimary = ContextCompat.getColor(getContext(), R.color.colorPrimary);
            int colorPrimaryDark = ContextCompat.getColor(getActivity().getApplicationContext(), R.color.colorPrimaryDark);

            for (int i = mySteps.length -1; i <= 0; i--) {

                mySteps[k] = recipeData.getSteps().get(i).getShortDescription();
                k++;

            }

            /*mAdapter = new RecipeDetailAdapter(recipeData, mListener);

            LinearLayoutManager manager = new LinearLayoutManager(getContext());
            manager.setOrientation(LinearLayoutManager.VERTICAL);

            mRecipeSteps.setAdapter(mAdapter);
            mRecipeSteps.setLayoutManager(manager);

            mRecipeSteps.setHasFixedSize(true);*/

            verticalStepperForm = view.findViewById(R.id.vertical_stepper_form);


            // Setting up and initializing the form
            VerticalStepperFormLayout.Builder.newInstance(verticalStepperForm, mySteps, this, getActivity())
                    .primaryColor(colorPrimary)
                    .primaryDarkColor(colorPrimaryDark)
                    .displayBottomNavigation(false) // It is true by default, so in this case this line is not necessary
                    .init();


        }

        else {

            //Error Condition
            //noDetails.setVisibility(View.VISIBLE);
            verticalStepperForm.setVisibility(View.GONE);

        }

    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * Methods for implementing Material Stepper form
     * @param stepNumber
     * @return
     */
    @Override
    public View createStepContentView(int stepNumber) {

        //inflating view from XML file:R.layout.step_layout and setting text to the TextView in it.
        View view = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.stpper_layout, null, false);
        TextView stepShortDescription = view.findViewById(R.id.stepper_description_text_sl);
        stepShortDescription.setText(recipeData.getSteps().get(stepNumber).getShortDescription());
        return view;
    }

    @Override
    public void onStepOpening(int stepNumber) {

        verticalStepperForm.setActiveStepAsCompleted();

    }

    @Override
    public void sendData() {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(int position);
    }
}
