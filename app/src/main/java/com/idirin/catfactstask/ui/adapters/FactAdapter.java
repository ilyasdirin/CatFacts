package com.idirin.catfactstask.ui.adapters;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.idirin.catfactstask.dagger.AppModule;
import com.idirin.catfactstask.dagger.DaggerDiComponent;
import com.idirin.catfactstask.dagger.DiModule;
import com.idirin.catfactstask.ui.holders.FactHolder;
import com.idirin.catfactstask.ui.mvp.facts.FactsMvp;


/**
 * Created by
 * idirin on 01/11/2017...
 */

public class FactAdapter extends RecyclerView.Adapter<FactHolder> {

    FactsMvp.ViewPresenterOps mPresenter;

    public FactAdapter(@NonNull FactsMvp.ViewPresenterOps presenter) {
        mPresenter = presenter;
        DaggerDiComponent.builder()
                .appModule(new AppModule((Application)mPresenter.getView().getActivityContext().getApplicationContext()))
                .diModule(new DiModule())
                .build().inject(this);
    }

    @Override
    public FactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return mPresenter.createViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(FactHolder holder, int position) {
        mPresenter.bindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return mPresenter.getFactCount();
    }

}
