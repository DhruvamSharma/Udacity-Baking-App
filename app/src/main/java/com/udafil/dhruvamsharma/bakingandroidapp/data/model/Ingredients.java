package com.udafil.dhruvamsharma.bakingandroidapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ingredients implements Parcelable
{
    //Changed to double, because the data was inconsistent
    @SerializedName("quantity")
    @Expose
    private Double quantity;
    @SerializedName("measure")
    @Expose
    private String measure;
    @SerializedName("Ingredients")
    @Expose
    private String Ingredients;
    public final static Parcelable.Creator<Ingredients> CREATOR = new Creator<Ingredients>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Ingredients createFromParcel(Parcel in) {
            return new Ingredients(in);
        }

        public Ingredients[] newArray(int size) {
            return (new Ingredients[size]);
        }

    }
            ;

    protected Ingredients(Parcel in) {
        this.quantity = ((Double) in.readValue((Integer.class.getClassLoader())));
        this.measure = ((String) in.readValue((String.class.getClassLoader())));
        this.Ingredients = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public Ingredients() {
    }

    /**
     *
     * @param measure
     * @param Ingredients
     * @param quantity
     */
    public Ingredients(Double quantity, String measure, String Ingredients) {
        super();
        this.quantity = quantity;
        this.measure = measure;
        this.Ingredients = Ingredients;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredients() {
        return Ingredients;
    }

    public void setIngredients(String Ingredients) {
        this.Ingredients = Ingredients;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(quantity);
        dest.writeValue(measure);
        dest.writeValue(Ingredients);
    }

    public int describeContents() {
        return 0;
    }

}