package br.com.rafael.seriespopulares.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rafael on 9/25/16.
 **/

public class Images implements Parcelable {

    private static final String FANART = "fanart";
    private static final String BANNER = "banner";

    @SerializedName(FANART)
    private Fanart fanart;

    @SerializedName(BANNER)
    private Banner banner;

    public Fanart getFanart() {
        return fanart;
    }

    public Images() {

    }

    protected Images(Parcel in) {
        this.fanart = in.readParcelable(Fanart.class.getClassLoader());
        this.banner = in.readParcelable(Banner.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(fanart, flags);
        dest.writeParcelable(banner, flags);
    }

    public static final Creator<Images> CREATOR = new Creator<Images>() {
        @Override
        public Images createFromParcel(Parcel source) {
            return new Images(source);
        }

        @Override
        public Images[] newArray(int size) {
            return new Images[size];
        }
    };
}
