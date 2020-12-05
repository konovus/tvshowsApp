package com.konovus.tvshowsapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;

public class Episode implements Parcelable {

    private String season;
    private String episode;
    private String name;
    private String air_date;

    protected Episode(Parcel in) {
        season = in.readString();
        episode = in.readString();
        name = in.readString();
        air_date = in.readString();
    }

    public static final Creator<Episode> CREATOR = new Creator<Episode>() {
        @Override
        public Episode createFromParcel(Parcel in) {
            return new Episode(in);
        }

        @Override
        public Episode[] newArray(int size) {
            return new Episode[size];
        }
    };

    public String getSeason() {
        return season;
    }

    public String getEpisode() {
        return episode;
    }

    public String getName() {
        return name;
    }

    public String getAir_date() {
        return air_date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(season);
        dest.writeString(episode);
        dest.writeString(name);
        dest.writeString(air_date);
    }
}
