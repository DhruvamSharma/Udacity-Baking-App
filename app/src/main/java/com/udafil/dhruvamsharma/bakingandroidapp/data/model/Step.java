package com.udafil.dhruvamsharma.bakingandroidapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Step implements Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("shortDescription")
    @Expose
    private String shortDescription;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("videoURL")
    @Expose
    private String videoURL;
    @SerializedName("thumbnailURL")
    @Expose
    private String thumbnailURL;
    public final static Parcelable.Creator<Step> CREATOR = new Creator<Step>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        public Step[] newArray(int size) {
            return (new Step[size]);
        }

    }
            ;

    protected Step(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.shortDescription = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.videoURL = ((String) in.readValue((String.class.getClassLoader())));
        this.thumbnailURL = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public Step() {
    }

    /**
     *
     * @param id
     * @param shortDescription
     * @param description
     * @param videoURL
     * @param thumbnailURL
     */
    public Step(Integer id, String shortDescription, String description, String videoURL, String thumbnailURL) {
        super();
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(shortDescription);
        dest.writeValue(description);
        dest.writeValue(videoURL);
        dest.writeValue(thumbnailURL);
    }

    public int describeContents() {
        return 0;
    }

}