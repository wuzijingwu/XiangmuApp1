package com.smile.taobaodemo.model;

import com.smile.taobaodemo.dao.MyApplication;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;



public class SeekActivityModle {
    public SeekActivityModle() {

    }

    public void getData(String uri, final Seek_DataCallBack<String> seek_dataCallBack) {
        Request build = new Request.Builder().url(uri).build();
        MyApplication.okHttpClient().newCall(build).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    seek_dataCallBack.getDataSucced(response.body().string());
                }
            }
        });
    }

    public interface Seek_DataCallBack<T> {
        void getDataSucced(T result);
    }
}
