package com.smile.taobaodemo.presenter;

/**
 * Created by dell on 2017/11/13.
 */


import com.smile.taobaodemo.model.Register_Model;
import com.smile.taobaodemo.ui.activity.RegisterActivity;
import com.smile.taobaodemo.ui.fragment.BasePresenter;
import com.smile.taobaodemo.view.Register_View;


public class Register_Presenter extends BasePresenter<Register_View> {

    private Register_Model model;

    public Register_Presenter(RegisterActivity iview) {
        super(iview);

    }

    public void getData(String url) {
        model.getData(url, new Register_Model.RegisterDataCallBack<String>() {
            @Override
            public void DataCallBack(String result) {
                Iview.onSuccedData(result);
            }

            @Override
            public void DataCallBackFaild(String result) {
                Iview.onFiledData(result);
            }
        });
    }

    @Override
    public void InitModel() {
        model = new Register_Model();
    }
}
