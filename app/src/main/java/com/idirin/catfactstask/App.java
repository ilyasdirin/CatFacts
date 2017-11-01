package com.idirin.catfactstask;

import android.app.Application;

import com.idirin.catfactstask.dagger.AppModule;
import com.idirin.catfactstask.dagger.DaggerDiComponent;
import com.idirin.catfactstask.dagger.DiModule;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by
 * idirin on 01/11/2017...
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        DaggerDiComponent.builder()
                .appModule(new AppModule(this))
                .diModule(new DiModule())
                .build();


        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }


}
