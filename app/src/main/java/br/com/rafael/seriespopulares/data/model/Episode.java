package br.com.rafael.seriespopulares.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rafael on 9/26/16.
 **/

public class Episode implements Parcelable {

    private static final String SEASON = "season";
    private static final String NUMBER = "number";
    private static final String TITLE = "title";
    private static final String IDS = "ids";

    @SerializedName(SEASON)
    private int season;

    @SerializedName(NUMBER)
    private int number;

    @SerializedName(TITLE)
    private String title;

    @SerializedName(IDS)
    private Ids ids;

    public int getNumber() {
        return number;
    }

    public String getTitle() {
        return title;
    }

    public Episode() {

    }

    protected Episode(Parcel in) {
        this.season = in.readInt();
        this.number = in.readInt();
        this.title = in.readString();
        this.ids = in.readParcelable(Ids.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(season);
        dest.writeInt(number);
        dest.writeString(title);
        dest.writeParcelable(ids, flags);
    }

    public static final Creator<Episode> CREATOR = new Creator<Episode>() {
        @Override
        public Episode createFromParcel(Parcel source) {
            return new Episode(source);
        }

        @Override
        public Episode[] newArray(int size) {
            return new Episode[size];
        }
    };
}
