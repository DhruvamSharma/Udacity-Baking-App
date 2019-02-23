package com.udafil.dhruvamsharma.bakingandroidapp.main;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.udafil.dhruvamsharma.bakingandroidapp.R;
import com.udafil.dhruvamsharma.bakingandroidapp.data.model.RecipeModel;
import com.udafil.dhruvamsharma.bakingandroidapp.recipeDetail.RecipeDetail;

import org.parceler.Parcels;

import java.util.List;

/**
 * Adapter class for Recipe List obtained from ViewModel
 */
public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder>{

    private static List<RecipeModel> recipeModel;
    private Context context;

    private int[] colorList = {R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark, R.color.pattensBlue};


    public RecipeListAdapter(List<RecipeModel> data, Context context) {
        recipeModel = data;
        this.context = context;
    }




    /**
     * View holder Class for Main Activity Adapter that contains two text views
     */
    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        TextView mTitle;
        TextView mServings;
        CardView viewHolderMainCV;

        public RecipeViewHolder(View itemView) {
            super(itemView);

            mTitle = itemView.findViewById(R.id.view_holder_main_title_tv);
            mServings = itemView.findViewById(R.id.view_holder_main_servings_tv);
            viewHolderMainCV = itemView.findViewById(R.id.view_holder_main_cv);
            //on clicking a card, detail activity should open
            viewHolderMainCV.setOnClickListener((view)-> {

                Intent intent = new Intent(context, RecipeDetail.class);
                Parcelable wrapped = Parcels.wrap(recipeModel.get(getAdapterPosition()));
                intent.putExtra(context.getPackageName(), wrapped);
                context.startActivity(intent);

            });
        }
    }


    /**
     * This method provides a new View Holder to the recyclerView for displaying data
     * @param parent
     * @param viewType
     * @return RecipeHolder
     */
    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_activity_main, parent, false);

        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {

        holder.mTitle.setText(recipeModel.get(position).getName());
        holder.mServings.setText("Servings: " + recipeModel.get(position).getServings());
        //holder.viewHolderMainCV.setBackgroundColor(colorList[position]);
    }

    /**
     * If the data is null, send 0 as item count and if not then
     * send the array length of the model data
     * @return
     */
    @Override
    public int getItemCount() {
        if(recipeModel == null)
            return 0;
        return recipeModel.size();
    }


    /**
     * public method to set the new data to the recycler view once the data is updated or added.
     * @param newData
     */
    public void switchAdapter(List<RecipeModel> newData) {
        recipeModel = newData;
        notifyDataSetChanged();
    }
}
