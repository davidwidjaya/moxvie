package com.davidwidjaya.moxvie.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Movie")
public class Movie {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    private int Id;

    @ColumnInfo(name = "poster_path")
    private String ImageUrl;

    @ColumnInfo(name = "original_title")
    private String Name;

    @ColumnInfo(name = "vote_average")
    private Float Rating;

    @ColumnInfo(name = "backdrop_path")
    private String Backdrop;

    @ColumnInfo(name = "release_date")
    private String Release;

    @ColumnInfo(name = "overview")
    private String Overview;

    @ColumnInfo(name = "popularity")
    private Float Popularity;

    public Movie() {
    }

    public Movie(int id, String imageUrl, String name, Float rating, String backdrop, String release, String overview, Float popularity) {
        Id = id;
        ImageUrl = imageUrl;
        Name = name;
        Rating = rating;
        Backdrop = backdrop;
        Release = release;
        Overview = overview;
        Popularity = popularity;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Float getRating() {
        return Rating;
    }

    public void setRating(Float rating) {
        Rating = rating;
    }

    public String getBackdrop() {
        return Backdrop;
    }

    public void setBackdrop(String backdrop) {
        Backdrop = backdrop;
    }

    public String getRelease() {
        return Release;
    }

    public void setRelease(String release) {
        Release = release;
    }

    public String getOverview() {
        return Overview;
    }

    public void setOverview(String overview) {
        Overview = overview;
    }

    public Float getPopularity() {
        return Popularity;
    }

    public void setPopularity(Float popularity) {
        Popularity = popularity;
    }
}
