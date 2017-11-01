package com.idirin.catfactstask.dagger;

import com.idirin.catfactstask.ui.adapters.FactAdapter;
import com.idirin.catfactstask.ui.fragments.BaseFragment;
import com.idirin.catfactstask.ui.mvp.facts.FactsPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by
 * idirin on 01/11/2017.
 */

@Singleton
@Component(modules = {AppModule.class, DiModule.class})
public interface DiComponent {

    void inject(BaseFragment baseFragment);

    void inject(FactAdapter factAdapter);

    void inject(FactsPresenter factsPresenter);

}
