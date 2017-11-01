package com.idirin.catfactstask.service.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.idirin.catfactstask.service.models.BaseModel;
import com.idirin.catfactstask.service.models.FactModel;

import java.util.List;

/**
 * Created by
 * idirin on 01/11/2017...
 */

public class FactResponse extends BaseModel {

    @SerializedName("data")
    @Expose
    private List<FactModel> facts = null;

    public List<FactModel> getFacts() {
        return facts;
    }

    public void setFacts(List<FactModel> data) {
        this.facts = data;
    }

}
