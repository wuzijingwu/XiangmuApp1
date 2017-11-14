package com.smile.taobaodemo.model;

import com.smile.taobaodemo.dao.MyApplication;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



public class Fm_FenLei_model {
    public Fm_FenLei_model() {

    }

    //左侧的请求数据
    public void getLeftData(String url, final DataCallBack<String> dataCallBack) {
        //调用OkHttpClient请求数据
        OkHttpClient okHttpClient = MyApplication.okHttpClient();
        Request build = new Request.Builder().url(url).build();
        okHttpClient.newCall(build).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    dataCallBack.getDataSucced(response.body().string());
                }
            }
        });
    }

    //右侧的请求数据
    public void getRightData(String url, final DataCallBack<String> mDataCallBack) {
        Request build = new Request.Builder().url(url).build();
        MyApplication.okHttpClient().newCall(build).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    mDataCallBack.getDataSucced(response.body().string());
                }
            }
        });
    }

    public interface DataCallBack<T> {
        void getDataSucced(T result);
    }
}
