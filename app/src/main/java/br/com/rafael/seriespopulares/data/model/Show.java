package br.com.rafael.seriespopulares.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Iterator;
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

    private boolean favorite;

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

    public boolean isFavorite() {
        return favorite;
    }

    public int getYear() {
        return year;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public double getRating() {
        return rating;
    }

    public Ids getIds() {
        return ids;
    }

    public Images getImages() {
        return images;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setIds(Ids ids) {
        this.ids = ids;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getGenres() {
        StringBuilder builder = new StringBuilder();
        Iterator<String> iterator = genres.iterator();
        while (iterator.hasNext()) {
            builder.append(iterator.next());
            if (iterator.hasNext()) {
                builder.append(", ");
            }
        }
        return builder.toString();
    }

    public String getTextShare() {
        String msg = "";
        msg += title + "\n\n";
        msg += "Overview" + "\n\n";
        msg += overview;
        return msg;
    }

    public Show() {

    }

    protected Show(Parcel in) {
        this.favorite = in.readInt() == 1;
        this.year = in.readInt();
        this.title = in.readString();
        this.overview = in.readString();
        this.rating = in.readDouble();
        this.ids = in.readParcelable(Ids.class.getClassLoader());
        this.images = in.readParcelable(Images.class.getClassLoader());
        genres = new ArrayList<>();
        in.readStringList(this.genres);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(favorite ? 1 : 0);
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
