package com.udafil.dhruvamsharma.bakingandroidapp.data.model;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.udafil.dhruvamsharma.bakingandroidapp.data.model.Ingredients;
import com.udafil.dhruvamsharma.bakingandroidapp.data.model.Step;


public class RecipeModel implements Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("ingredients")
    @Expose
    private List<Ingredients> ingredients = null;
    @SerializedName("steps")
    @Expose
    private List<Step> steps = null;
    @SerializedName("servings")
    @Expose
    private Integer servings;
    @SerializedName("image")
    @Expose
    private String image;
    public final static Parcelable.Creator<RecipeModel> CREATOR = new Creator<RecipeModel>() {


        @SuppressWarnings({
                "unchecked"
        })
        public RecipeModel createFromParcel(Parcel in) {
            return new RecipeModel(in);
        }

        public RecipeModel[] newArray(int size) {
            return (new RecipeModel[size]);
        }

    }
            ;

    protected RecipeModel(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.ingredients, (com.udafil.dhruvamsharma.bakingandroidapp.data.model.Ingredients.class.getClassLoader()));
        in.readList(this.steps, (com.udafil.dhruvamsharma.bakingandroidapp.data.model.Step.class.getClassLoader()));
        this.servings = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.image = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public RecipeModel() {
    }

    /**
     *
     * @param ingredients
     * @param id
     * @param servings
     * @param name
     * @param image
     * @param steps
     */
    public RecipeModel(Integer id, String name, List<Ingredients> ingredients, List<Step> steps, Integer servings, String image) {
        super();
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public Integer getServings() {
        return servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeList(ingredients);
        dest.writeList(steps);
        dest.writeValue(servings);
        dest.writeValue(image);
    }

    public int describeContents() {
        return 0;
    }

}