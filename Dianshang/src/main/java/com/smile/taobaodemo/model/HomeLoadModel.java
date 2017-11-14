package com.smile.taobaodemo.model;

import android.content.Context;

import com.smile.taobaodemo.model.entity.HomeBottom;
import com.smile.taobaodemo.model.entity.HomeTop;
import com.smile.taobaodemo.presenter.OnLoadListener;



public interface HomeLoadModel extends LoadModel {

    void load(OnLoadListener<HomeTop> listener, Context context, int type);

    void load(OnLoadListener<HomeBottom> listener, Context context, int type, int page, int pageSize);
}
