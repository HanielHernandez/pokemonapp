package com.aim.myapplication.networking;



import com.aim.myapplication.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseRemote {


    protected <T> T create(Class<T> clazz) {
        return retrofit().create(clazz);
    }



    private Retrofit retrofit() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
//        if (BuildConfig.DEBUG) okHttpBuilder.addInterceptor(loggingInterceptor);
        OkHttpClient client = okHttpBuilder
                //.addInterceptor(new AuthInterceptor())
                .connectTimeout(BuildConfig.DEBUG?10:4, TimeUnit.SECONDS)
                .pingInterval(BuildConfig.DEBUG?10:2, TimeUnit.SECONDS)
                .readTimeout(BuildConfig.DEBUG?30:10, TimeUnit.SECONDS)
                .build();

        return new Retrofit.Builder().baseUrl(BuildConfig.API_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

    }

}
