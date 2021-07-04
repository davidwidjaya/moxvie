package com.davidwidjaya.moxvie.data.remote;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public class SearchResponse implements Parcelable {
    @PrimaryKey
    @ColumnInfo(name = "id")
    @NonNull
    private int Id;
    private String ImageUrl;
    private String Name;
    private Float Rating;
    private String Backdrop;
    private String Release;
    private String Overview;
    private Float Popularity;
    private String MovieTv;

    public SearchResponse(int id, String imageUrl, String name, Float rating, String backdrop, String release, String overview, Float popularity, String movieTv) {
        Id = id;
        ImageUrl = imageUrl;
        Name = name;
        Rating = rating;
        Backdrop = backdrop;
        Release = release;
        Overview = overview;
        Popularity = popularity;
        MovieTv = movieTv;
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

    public String getMovieTv() {
        return MovieTv;
    }

    public void setMovieTv(String movieTv) {
        MovieTv = movieTv;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.Id);
        dest.writeString(this.ImageUrl);
        dest.writeString(this.Name);
        dest.writeValue(this.Rating);
        dest.writeString(this.Backdrop);
        dest.writeString(this.Release);
        dest.writeString(this.Overview);
        dest.writeValue(this.Popularity);
        dest.writeString(this.MovieTv);
    }

    public void readFromParcel(Parcel source) {
        this.Id = source.readInt();
        this.ImageUrl = source.readString();
        this.Name = source.readString();
        this.Rating = (Float) source.readValue(Float.class.getClassLoader());
        this.Backdrop = source.readString();
        this.Release = source.readString();
        this.Overview = source.readString();
        this.Popularity = (Float) source.readValue(Float.class.getClassLoader());
        this.MovieTv = source.readString();
    }

    protected SearchResponse(Parcel in) {
        this.Id = in.readInt();
        this.ImageUrl = in.readString();
        this.Name = in.readString();
        this.Rating = (Float) in.readValue(Float.class.getClassLoader());
        this.Backdrop = in.readString();
        this.Release = in.readString();
        this.Overview = in.readString();
        this.Popularity = (Float) in.readValue(Float.class.getClassLoader());
        this.MovieTv = in.readString();
    }

    public static final Creator<SearchResponse> CREATOR = new Creator<SearchResponse>() {
        @Override
        public SearchResponse createFromParcel(Parcel source) {
            return new SearchResponse(source);
        }

        @Override
        public SearchResponse[] newArray(int size) {
            return new SearchResponse[size];
        }
    };

    public static SearchResponse createFromParcel(Parcel source) {
        return CREATOR.createFromParcel(source);
    }

    public static SearchResponse[] newArray(int size) {
        return CREATOR.newArray(size);
    }
}
