package com.example.task1.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import android.os.Parcel;
import android.os.Parcelable;

public class Price implements Parcelable {

    @SerializedName("priceUsd")
    @Expose
    private String priceUsd;

    @SerializedName("time")
    @Expose
    private String time;

    public Price(String priceUsd, String time) {
        this.priceUsd = priceUsd;
        this.time = time;
    }

    public String getPriceUsd() {
        return priceUsd;
    }

    public void setPriceUsd(String priceUsd) {
        this.priceUsd = priceUsd;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.priceUsd);
        dest.writeString(this.time);
    }

    protected Price(Parcel in) {
        this.priceUsd = in.readString();
        this.time = in.readString();
    }

    public static final Parcelable.Creator<Price> CREATOR = new Parcelable.Creator<Price>() {
        @Override
        public Price createFromParcel(Parcel source) {
            return new Price(source);
        }

        @Override
        public Price[] newArray(int size) {
            return new Price[size];
        }
    };
}
