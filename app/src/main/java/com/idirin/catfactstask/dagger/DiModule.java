package com.idirin.catfactstask.dagger;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.idirin.catfactstask.BuildConfig;
import com.idirin.catfactstask.event.OttoEventBus;
import com.idirin.catfactstask.service.RestInterface;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by
 * idirin on 01/11/2017.
 */

@Module
public class DiModule {

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(Application application){
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    @Singleton
    SharedPreferences.Editor providePrefEditor(SharedPreferences sharedPreferences){
        return sharedPreferences.edit();
    }

    @Provides
    @Singleton
    Cache provideOkHttpCache(Application application) {
        int cacheSize = 10*1024*1024;
        return new Cache(application.getCacheDir(), cacheSize);
    }

    @Provides
    @Singleton
    Gson provideGson(){
        return new GsonBuilder()
                .setLenient()
                .create();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Cache cache){
        return new OkHttpClient.Builder()
                .connectTimeout(BuildConfig.CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(BuildConfig.CONNECTION_READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(BuildConfig.CONNECTION_WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                .cache(cache)
                .build();
    }

    @Provides
    @Singleton
    RestInterface provideRestInterface(Gson gson, OkHttpClient okHttpClient){
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
                .create(RestInterface.class);
    }

    @Provides
    @Singleton
    OttoEventBus provideBus() {
        return new OttoEventBus();
    }

}


























