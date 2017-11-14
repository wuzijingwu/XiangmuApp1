package com.smile.taobaodemo.ui.fragment;

import android.content.Context;

import com.smile.taobaodemo.dao.MyApplication;
import com.smile.taobaodemo.view.IView;


public abstract class BasePresenter<T extends IView> {
    protected T Iview;

    public BasePresenter(T iview) {
        this.Iview = iview;
        InitModel();
    }

    public abstract void InitModel();

    Context Basecontext() {
        if (Iview != null && Iview.context() != null) {
            return Iview.context();
        }
        return MyApplication.AppContext();
    }
}
