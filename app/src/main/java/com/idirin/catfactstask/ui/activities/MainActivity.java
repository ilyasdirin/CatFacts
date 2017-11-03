package com.idirin.catfactstask.ui.activities;

import android.support.v7.app.ActionBar;

import com.idirin.catfactstask.R;
import com.idirin.catfactstask.ui.mvp.facts.FactsFragment;


/**
 * Created by
 * idirin on 01/11/2017...
 */

public class MainActivity extends BaseActivity {

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    public int getTitleResId() {
        return R.string.app_name;
    }

    @Override
    public void init() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(getResources().getDrawable(R.mipmap.ic_launcher_round));
        }
        replaceFragment(R.id.frameLayout, new FactsFragment());
    }

}
