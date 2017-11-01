package com.idirin.catfactstask.ui.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idirin.catfactstask.dagger.AppModule;
import com.idirin.catfactstask.dagger.DaggerDiComponent;
import com.idirin.catfactstask.dagger.DiModule;
import com.idirin.catfactstask.event.OttoEventBus;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by
 * idirin on 01/10/2017...
 */

public abstract class BaseFragment extends Fragment {

    protected Activity activity;
    protected View view;
    protected Unbinder unbinder;


    @Inject
    protected OttoEventBus eventBus;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        activity = getActivity();
        view = getView();
        activity.setTitle(getTitleResId());

        DaggerDiComponent.builder()
                .appModule(new AppModule(activity.getApplication()))
                .diModule(new DiModule())
                .build().inject(this);

        init();
    }

    @Override
    public void onResume() {
        super.onResume();

        eventBus.register(this);
    }

    @Override
    public void onDestroyView() {

        unbinder.unbind();

        eventBus.unregister(this);

        super.onDestroyView();
    }


    protected abstract @LayoutRes int getLayoutResId();
    protected abstract @StringRes int getTitleResId();
    protected abstract void setupMvp();
    protected abstract void init();

}
