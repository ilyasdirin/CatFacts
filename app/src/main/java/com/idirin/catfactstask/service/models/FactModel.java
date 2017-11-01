package com.idirin.catfactstask.service.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by
 * idirin on 01/11/2017...
 */

public class FactModel {

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
}
