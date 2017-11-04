package com.idirin.catfactstask.ui.mvp.facts;

import android.os.Parcel;
import android.os.Parcelable;

import com.idirin.catfactstask.service.models.FactModel;

import java.util.List;

/**
 * Created by
 * idirin on 03/11/2017...
 */

public class FactsState implements Parcelable {

    private List<FactModel> facts;
    private int progress;
    private int paging;
    private int totalFactCount;

    public FactsState(int progress, int totalFactCount, List<FactModel> facts, int paging) {
        this.progress = progress;
        this.totalFactCount = totalFactCount;
        this.facts = facts;
        this.paging = paging;
    }

    public List<FactModel> getFacts() {
        return facts;
    }

    public int getProgress() {
        return progress;
    }

    public int getPaging() { return paging; }

    public int getTotalFactCount() { return totalFactCount; }




    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(facts);
        parcel.writeInt(progress);
        parcel.writeInt(paging);
        parcel.writeInt(totalFactCount);
    }

    private FactsState(Parcel in) {
        facts = in.createTypedArrayList(FactModel.CREATOR);
        progress = in.readInt();
        paging = in.readInt();
        totalFactCount = in.readInt();
    }

    public static final Parcelable.Creator<FactsState> CREATOR
            = new Parcelable.Creator<FactsState>() {
        public FactsState createFromParcel(Parcel in) {
            return new FactsState(in);
        }

        public FactsState[] newArray(int size) {
            return new FactsState[size];
        }
    };


}
