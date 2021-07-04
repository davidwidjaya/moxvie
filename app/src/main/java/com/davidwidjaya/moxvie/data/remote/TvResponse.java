package com.davidwidjaya.moxvie.data.remote;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

public class TvResponse implements Parcelable {
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

    public TvResponse(int id, String imageUrl, String name, Float rating, String backdrop, String release, String overview, Float popularity) {
        Id = id;
        ImageUrl = imageUrl;
        Name = name;
        Rating = rating;
        Backdrop = backdrop;
        Release = release;
        Overview = overview;
        Popularity = popularity;
    }

    protected TvResponse(Parcel in) {
        Id = in.readInt();
        ImageUrl = in.readString();
        Name = in.readString();
        if (in.readByte() == 0) {
            Rating = null;
        } else {
            Rating = in.readFloat();
        }
        Backdrop = in.readString();
        Release = in.readString();
        Overview = in.readString();
        if (in.readByte() == 0) {
            Popularity = null;
        } else {
            Popularity = in.readFloat();
        }
    }

    public static final Creator<TvResponse> CREATOR = new Creator<TvResponse>() {
        @Override
        public TvResponse createFromParcel(Parcel in) {
            return new TvResponse(in);
        }

        @Override
        public TvResponse[] newArray(int size) {
            return new TvResponse[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Id);
        dest.writeString(ImageUrl);
        dest.writeString(Name);
        if (Rating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(Rating);
        }
        dest.writeString(Backdrop);
        dest.writeString(Release);
        dest.writeString(Overview);
        if (Popularity == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(Popularity);
        }
    }
}
