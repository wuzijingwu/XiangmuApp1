package com.smile.taobaodemo.presenter;


import com.smile.taobaodemo.model.SeekActivityModle;
import com.smile.taobaodemo.ui.fragment.BasePresenter;
import com.smile.taobaodemo.view.SeekActivity_View;

public class SeekActivity_presenter extends BasePresenter<SeekActivity_View> {

    private SeekActivityModle modle;

    public SeekActivity_presenter(SeekActivity_View iview) {
        super(iview);
    }

    public void getData(String uri) {
        modle.getData(uri, new SeekActivityModle.Seek_DataCallBack<String>() {
            @Override
            public void getDataSucced(String result) {
                Iview.getResultSucced(result);
            }
        });
    }

    @Override
    public void InitModel() {
        modle = new SeekActivityModle();
    }
}
