package com.konovus.tvshowsapp.models;

import com.google.gson.annotations.SerializedName;

public class TVShow {

    private int id;
    private String name;
    @SerializedName("start_date")
    private String startDate;
    private String country;
    private String network;
    private String status;
    @SerializedName("image_thumbnail_path")
    private String thumbnail;

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
