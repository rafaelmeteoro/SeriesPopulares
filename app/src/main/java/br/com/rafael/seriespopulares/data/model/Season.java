package br.com.rafael.seriespopulares.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafael on 9/26/16.
 **/

public class Season implements Parcelable {

    private static final String NUMBER = "number";
    private static final String IDS = "ids";
    private static final String EPISODES = "episodes";

    @SerializedName(NUMBER)
    private int number;

    @SerializedName(IDS)
    private Ids ids;

    @SerializedName(EPISODES)
    private List<Episode> episodes;

    public int getNumber() {
        return number;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setIds(Ids ids) {
        this.ids = ids;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }

    public Season() {

    }

    protected Season(Parcel in) {
        this.number = in.readInt();
        this.ids = in.readParcelable(Ids.class.getClassLoader());
        episodes = new ArrayList<>();
        in.readList(episodes, Episode.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(number);
        dest.writeParcelable(ids, flags);
        dest.writeList(episodes);
    }

    public static final Creator<Season> CREATOR = new Creator<Season>() {
        @Override
        public Season createFromParcel(Parcel source) {
            return new Season(source);
        }

        @Override
        public Season[] newArray(int size) {
            return new Season[size];
        }
    };
}
