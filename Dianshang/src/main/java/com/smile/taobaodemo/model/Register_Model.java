package com.smile.taobaodemo.model;


import com.smile.taobaodemo.dao.MyApplication;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;



public class Register_Model {
    public void getData(String url, final RegisterDataCallBack<String> registerDataCallBack) {
        Request build = new Request.Builder().url(url).build();
        MyApplication.okHttpClient().newCall(build).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                registerDataCallBack.DataCallBackFaild(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    registerDataCallBack.DataCallBack(response.body().string());
                }
            }
        });
    }

    public interface RegisterDataCallBack<T> {
        void DataCallBack(T result);

        void DataCallBackFaild(T result);
    }
}
