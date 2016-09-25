package br.com.rafael.seriespopulares.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rafael on 9/25/16.
 **/

public class Ids implements Parcelable {

    private static final String TRAKT = "trakt";
    private static final String SLUG = "slug";
    private static final String TVDB = "tvdb";
    private static final String IMDB = "imdb";
    private static final String TMDB = "tmdb";
    private static final String TVRAGE = "tvrage";

    @SerializedName(TRAKT)
    private int trakt;

    @SerializedName(TVDB)
    private int tvdb;

    @SerializedName(TMDB)
    private int tmdb;

    @SerializedName(TVRAGE)
    private int tvrage;

    @SerializedName(SLUG)
    private String slug;

    @SerializedName(IMDB)
    private String imdb;

    public Ids() {

    }

    protected Ids(Parcel in) {
        this.trakt = in.readInt();
        this.tvdb = in.readInt();
        this.tmdb= in.readInt();
        this.tvrage = in.readInt();
        this.slug = in.readString();
        this.imdb = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(trakt);
        dest.writeInt(tvdb);
        dest.writeInt(tmdb);
        dest.writeInt(tvrage);
        dest.writeString(slug);
        dest.writeString(imdb);
    }

    public static final Creator<Ids> CREATOR = new Creator<Ids>() {
        @Override
        public Ids createFromParcel(Parcel source) {
            return new Ids(source);
        }

        @Override
        public Ids[] newArray(int size) {
            return new Ids[size];
        }
    };
}
