package com.smile.taobaodemo.presenter;


import com.smile.taobaodemo.model.Fm_FenLei_model;
import com.smile.taobaodemo.ui.fragment.BasePresenter;
import com.smile.taobaodemo.view.FM_FenLei_View;

public class Fm_FenLei_presenter extends BasePresenter<FM_FenLei_View> {

    private Fm_FenLei_model fm_fenLei_model;

    public Fm_FenLei_presenter(FM_FenLei_View iview) {
        super(iview);
    }

    //请求左侧数据
    public void getLeftData(String url) {
        fm_fenLei_model.getLeftData(url, new Fm_FenLei_model.DataCallBack<String>() {
            @Override
            public void getDataSucced(String result) {
//                onLeftResultSucced(result);
               Iview.onLeftResultSucced(result);
            }
        });
    }

    //请求右侧数据
    public void getRightData(String url) {
        fm_fenLei_model.getRightData(url, new Fm_FenLei_model.DataCallBack<String>() {
            @Override
            public void getDataSucced(String result) {
                Iview.onRightResultSucced(result);
            }
        });
    }

    @Override
    public void InitModel() {
        fm_fenLei_model = new Fm_FenLei_model();
    }
}
