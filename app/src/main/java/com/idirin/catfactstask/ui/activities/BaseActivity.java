package com.idirin.catfactstask.ui.activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;

import com.idirin.catfactstask.util.Constants;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by
 * idirin on 01/10/2017...
 */

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        unbinder = ButterKnife.bind(this);

        setTitle(getTitleResId());

        if (savedInstanceState == null) {
            init();
        }
    }


    public void replaceFragment(@IdRes int container, Fragment fragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(container, fragment, fragment.getClass().getSimpleName());
        transaction.setTransition(Constants.DEFAULT_FRAGMENT_TRANSITION_TYPE);
        transaction.commit();
    }

    @Override
    protected void onDestroy() {

        unbinder.unbind();

        super.onDestroy();
    }

    public abstract @LayoutRes int getLayoutResId();
    public abstract @StringRes int getTitleResId();
    public abstract void init();

}
