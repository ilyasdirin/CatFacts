package com.idirin.catfactstask.ui.mvp.facts;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idirin.catfactstask.R;
import com.idirin.catfactstask.dagger.AppModule;
import com.idirin.catfactstask.dagger.DaggerDiComponent;
import com.idirin.catfactstask.dagger.DiModule;
import com.idirin.catfactstask.event.OttoEventBus;
import com.idirin.catfactstask.event.ShareClickedEvent;
import com.idirin.catfactstask.service.RestInterface;
import com.idirin.catfactstask.service.models.FactModel;
import com.idirin.catfactstask.service.responses.FactResponse;
import com.idirin.catfactstask.ui.holders.FactHolder;
import com.idirin.catfactstask.ui.mvp.BaseMvp;
import com.idirin.catfactstask.util.Constants;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by
 * idirin on 01/11/2017...
 */

public class FactsPresenter implements FactsMvp.ViewPresenterOps, FactsMvp.ModelPresenterOps {

    @Inject
    RestInterface api;

    @Inject
    OttoEventBus bus;

    private WeakReference<FactsMvp.ViewOps> mView;
    private FactsMvp.ModelOps mModel;
    private int pageNum;

    private CompositeDisposable subscriptions = new CompositeDisposable();

    public FactsPresenter(FactsMvp.ViewOps view) {
        mView = new WeakReference<>(view);

        DaggerDiComponent.builder()
                .appModule(new AppModule((Application) getActivityContext().getApplicationContext()))
                .diModule(new DiModule())
                .build().inject(this);
    }



    /* MVP METHODS */
    @Override
    public void onDestroy() {
        subscriptions.clear();
        subscriptions.dispose();
        mModel.onDestroy();
    }

    @Override
    public FactsMvp.ViewOps getView() {
        return mView.get();
    }

    @Override
    public Context getActivityContext() {
        return getView().getActivityContext();
    }

    @Override
    public <T extends BaseMvp.ModelOps> void setModel(T model) {
        mModel = (FactsMvp.ModelOps)model;
    }

    @Override
    public void loadFacts(boolean newFactLength, int maxLength) {

        subscriptions.clear();

        getView().showLoading(true);

        if (newFactLength) {
            pageNum = 1;
            mModel.resetFacts();
        }

        Map<String, Object> params = new HashMap<>();
        params.put("page", pageNum);
        params.put("max_length", maxLength);
        params.put("limit", Constants.FACT_COUNT_LIMIT);

        api.getCatFacts(params)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FactResponse>() {

                    @Override
                    public void onNext(FactResponse response) {

                        mModel.appendFacts(response);
                        if (mModel.getFactCount() > response.getFacts().size()) {
                            getView().notifyDataRangeInserted(mModel.getFactCount()-response.getFacts().size(), response.getFacts().size());
                        } else {
                            getView().notifyDataSetChanged();
                        }

                        if (response.getFacts().size()>0) {
                            pageNum ++;
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        getView().showLoading(false);
                        mView.get().showToast(t.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        getView().showLoading(false);
                        getView().setFactCount(mModel.getTotalFactsCount());
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        subscriptions.add(d);
                    }

                });
    }

    @Override
    public FactHolder createViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_fact, parent, false);
        return new FactHolder(v, new com.idirin.catfactstask.ui.listeners.RecyclerViewClickListener() {
            @Override
            public void recyclerViewListClicked(View v, int position) {
                bus.post(new ShareClickedEvent(position));
            }
        });
    }

    @Override
    public void bindViewHolder(FactHolder holder, int position) {
        FactModel fact = mModel.getFacts().get(position);
        holder.txtCatFact.setText(fact.getFact());
        holder.txtLength.setText(getActivityContext().getString(R.string.fact_length_value, fact.getLength()));
        holder.txtIndex.setText(String.valueOf(position+1));
    }

    @Override
    public int getFactCount() {
        return mModel.getFactCount();
    }

    @Override
    public void share(int position) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, mModel.getFact(position).getFact());
        sendIntent.setType("text/plain");
        getActivityContext().startActivity(sendIntent);
    }

    @Override
    public void resetFacts() {
        mModel.resetFacts();
    }

}
