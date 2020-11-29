package com.konovus.tvshowsapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class TVShow implements Parcelable {

    private int id;
    private String name;
    @SerializedName("start_date")
    private String startDate;
    private String country;
    private String network;
    private String status;
    @SerializedName("image_thumbnail_path")
    private String thumbnail;

    protected TVShow(Parcel in) {
        id = in.readInt();
        name = in.readString();
        startDate = in.readString();
        country = in.readString();
        network = in.readString();
        status = in.readString();
        thumbnail = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(startDate);
        dest.writeString(country);
        dest.writeString(network);
        dest.writeString(status);
        dest.writeString(thumbnail);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TVShow> CREATOR = new Creator<TVShow>() {
        @Override
        public TVShow createFromParcel(Parcel in) {
            return new TVShow(in);
        }

        @Override
        public TVShow[] newArray(int size) {
            return new TVShow[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getCountry() {
        return country;
    }

    public String getNetwork() {
        return network;
    }

    public String getStatus() {
        return status;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}
