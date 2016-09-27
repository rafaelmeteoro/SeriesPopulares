package br.com.rafael.seriespopulares.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rafael on 9/25/16.
 **/

public class Fanart implements Parcelable {

    private static final String THUMB = "thumb";

    @SerializedName(THUMB)
    private String thumb;

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public Fanart() {

    }

    protected Fanart(Parcel in) {
        this.thumb = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(thumb);
    }

    public static final Creator<Fanart> CREATOR = new Creator<Fanart>() {
        @Override
        public Fanart createFromParcel(Parcel source) {
            return new Fanart(source);
        }

        @Override
        public Fanart[] newArray(int size) {
            return new Fanart[size];
        }
    };
}
