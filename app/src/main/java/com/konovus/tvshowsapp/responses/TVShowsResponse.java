package com.konovus.tvshowsapp.responses;

import com.google.gson.annotations.SerializedName;
import com.konovus.tvshowsapp.models.TVShow;

import java.util.List;

public class TVShowsResponse {

    private int page;
    private int pages;
    @SerializedName("tv_shows")
    private List<TVShow> tvShows;

    public int getPage() {
        return page;
    }

    public int getPages() {
        return pages;
    }

    public List<TVShow> getTvShows() {
        return tvShows;
    }
}
