package com.idirin.catfactstask.service;

import com.idirin.catfactstask.service.responses.FactResponse;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by
 * idirin on 01/11/2017.
 */

public interface RestInterface {

    @GET("facts")
    Observable<FactResponse> getCatFacts(@QueryMap Map<String, Object> params);

}
