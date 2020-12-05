package com.konovus.tvshowsapp.models;

import java.io.Serializable;
import java.util.List;

public class TVShowDetails implements Serializable {

    private String url;
    private String description;
    private String runtime;
    private String image_path;
    private String rating;
    private String[] genres;
    private String[] pictures;
    private List<Episode> episodes;

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getImage_path() {
        return image_path;
    }

    public String getRating() {
        return rating;
    }

    public String[] getGenres() {
        return genres;
    }

    public String[] getPictures() {
        return pictures;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }
}
