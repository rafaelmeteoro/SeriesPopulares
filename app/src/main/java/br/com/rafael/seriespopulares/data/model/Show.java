package br.com.rafael.seriespopulares.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by rafael on 9/25/16.
 **/

public class Show implements Parcelable {

    private static final String TITLE = "title";
    private static final String YEAR = "year";
    private static final String IDS = "ids";
    private static final String OVERVIEW = "overview";
    private static final String RATING = "rating";
    private static final String GENRES = "genres";
    private static final String IMAGES = "images";

    @SerializedName(YEAR)
    private int year;

    @SerializedName(TITLE)
    private String title;

    @SerializedName(OVERVIEW)
    private String overview;

    @SerializedName(RATING)
    private double rating;

    @SerializedName(IDS)
    private Ids ids;

    @SerializedName(IMAGES)
    private Images images;

    @SerializedName(GENRES)
    private List<String> genres;

    public int getYear() {
        return year;
    }

    public String getTitle() {
        return title;
    }

    public double getRating() {
        return rating;
    }

    public Images getImages() {
        return images;
    }

    public Show() {

    }

    protected Show(Parcel in) {
        this.year = in.readInt();
        this.title = in.readString();
        this.overview = in.readString();
        this.rating = in.readDouble();
        this.ids = in.readParcelable(Ids.class.getClassLoader());
        this.images = in.readParcelable(Images.class.getClassLoader());
        in.readStringList(this.genres);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(year);
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeDouble(rating);
        dest.writeParcelable(ids, flags);
        dest.writeParcelable(images, flags);
        dest.writeStringList(genres);
    }

    public static final Creator<Show> CREATOR = new Creator<Show>() {
        @Override
        public Show createFromParcel(Parcel source) {
            return new Show(source);
        }

        @Override
        public Show[] newArray(int size) {
            return new Show[size];
        }
    };
}
