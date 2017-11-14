package com.smile.taobaodemo.ui.contract;


import com.smile.taobaodemo.presenter.BasePresenter;
import com.smile.taobaodemo.view.BaseView;
import com.smile.taobaodemo.model.entity.HomeBottom;
import com.smile.taobaodemo.model.entity.HomeTop;



public interface HomeContract {
    interface View extends BaseView {
        void show(HomeTop bean);

        void show(HomeBottom bean);

        void loading();

        void networkError();

        void error(String msg);

        void showFailed(String msg);
    }

    interface Presenter extends BasePresenter {
        void start(int type);

        void start(int type, int page, int pageSize);
    }
}
