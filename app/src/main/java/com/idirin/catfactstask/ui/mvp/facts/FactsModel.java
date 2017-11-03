package com.idirin.catfactstask.ui.mvp.facts;

import android.support.annotation.NonNull;

import com.idirin.catfactstask.service.models.FactModel;
import com.idirin.catfactstask.service.responses.FactResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by
 * idirin on 01/11/2017...
 */

public class FactsModel implements FactsMvp.ModelOps {

    private FactsMvp.ModelPresenterOps mPresenter;
    private List<FactModel> facts;
    private int totalFactCount;

    public FactsModel(FactsMvp.ModelPresenterOps presenter) {
        mPresenter = presenter;
        facts = new ArrayList<>();
    }

    @Override
    public void onDestroy() {
        facts.clear();
        facts = null;
        totalFactCount = -1;
    }

    @Override
    public List<FactModel> getFacts() {
        return facts;
    }

    @Override
    public void resetFacts() {
        facts.clear();
        facts = new ArrayList<>();
        totalFactCount = -1;
    }

    @Override
    public void appendFacts(@NonNull FactResponse response) {
        facts.addAll(response.getFacts());
        totalFactCount = response.getTotal();
    }

    @Override
    public int getFactCount() {
        return facts.size();
    }

    @Override
    public FactModel getFact(int position) {
        return facts.get(position);
    }

    @Override
    public int getTotalFactsCount() {
        return totalFactCount;
    }
}
