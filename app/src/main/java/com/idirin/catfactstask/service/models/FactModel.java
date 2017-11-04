package com.idirin.catfactstask.service.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by
 * idirin on 01/11/2017...
 */

public class FactModel implements Parcelable {

    @SerializedName("fact")
    @Expose
    private String fact;
    @SerializedName("length")
    @Expose
    private Integer length;

    public String getFact() {
        return fact;
    }

    public void setFact(String fact) {
        this.fact = fact;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(fact);
        parcel.writeInt(length);
    }

    public static final Parcelable.Creator<FactModel> CREATOR = new Parcelable.Creator<FactModel>() {
        public FactModel createFromParcel(Parcel in) {
            return new FactModel(in);
        }

        public FactModel[] newArray(int size) {
            return new FactModel[size];
        }
    };

    private FactModel(Parcel in) {
        fact = in.readString();
        length = in.readInt();
    }


}
