package com.udafil.dhruvamsharma.bakingandroidapp.recipeDetail;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.udafil.dhruvamsharma.bakingandroidapp.R;
import com.udafil.dhruvamsharma.bakingandroidapp.data.model.RecipeModel;

import org.parceler.Parcels;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecipeDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class RecipeDetailFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private RecyclerView mRecipeSteps;
    private RecipeDetailAdapter mAdapter;
    private RecipeModel recipeData;
    private ImageView noDetails;

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

        noDetails = view.findViewById(R.id.no_food_iv);
        mRecipeSteps = view.findViewById(R.id.recipe_detail_fragment_steps_rv);

        Bundle bundle = null;

        if (getArguments() != null) {

            bundle = getArguments();

            recipeData = Parcels.unwrap(bundle.getParcelable(getContext().getPackageName()));


            Log.e(getContext().getPackageName(), recipeData.toString());



            mAdapter = new RecipeDetailAdapter(recipeData, mListener);

            LinearLayoutManager manager = new LinearLayoutManager(getContext());
            manager.setOrientation(LinearLayoutManager.VERTICAL);

            mRecipeSteps.setAdapter(mAdapter);
            mRecipeSteps.setLayoutManager(manager);

            mRecipeSteps.setHasFixedSize(true);



        }

        else {
            noDetails.setVisibility(View.VISIBLE);
            mRecipeSteps.setVisibility(View.GONE);
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
