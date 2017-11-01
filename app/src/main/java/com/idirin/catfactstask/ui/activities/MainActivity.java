package com.idirin.catfactstask.ui.activities;

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
        replaceFragment(R.id.frameLayout, new FactsFragment());
    }

}
