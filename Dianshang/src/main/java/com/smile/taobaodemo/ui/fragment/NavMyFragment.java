package com.smile.taobaodemo.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.smile.taobaodemo.R;
import com.smile.taobaodemo.ui.activity.LoginActivity;
import com.smile.taobaodemo.utils.FirstEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

//        model.utils.FirstEvent;
public class NavMyFragment extends Fragment implements View.OnClickListener {

    private View view;
    private RelativeLayout layout;
    private ImageView hander;
    private TextView login_register_tv;
    private SharedPreferences hander_sp;//保存头像

    //    fragment_navigation_user
    public static NavMyFragment newInstance() {
        NavMyFragment fragment = new NavMyFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注册EventBus
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getContext(), R.layout.fragment_navigation_user, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
        //加载默认显示头像
        if (hander_sp.getString("ImageKey", null) != null) {
            setImageByImageLoader(hander_sp.getString("ImageKey", null), hander);
        }
        //加载显示名称
        if (hander_sp.getString("NameKey", null) != null) {
            login_register_tv.setText(hander_sp.getString("NameKey", null));
        }
    }

    private void initView() {
        layout = (RelativeLayout) view.findViewById(R.id.login_register_layout);
        hander = (ImageView) view.findViewById(R.id.hander);//头像
        login_register_tv = (TextView) view.findViewById(R.id.login_register_tv);//注册或登录这个TextView
        layout.setOnClickListener(this);
        hander_sp = getActivity().getSharedPreferences("handerImage", Context.MODE_PRIVATE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_register_layout://跳转到登录页面
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
        }
    }

    @Subscribe
    public void onEventMainThread(FirstEvent event) {
        hander_sp.edit().putString("ImageKey", event.getImg()).commit();
        hander_sp.edit().putString("NameKey", event.getName()).commit();
        if (hander_sp.getString("ImageKey", null) != null) {
            setImageByImageLoader(hander_sp.getString("ImageKey", null), hander);
        }
        if (hander_sp.getString("NameKey", null) != null) {
            login_register_tv.setText(hander_sp.getString("NameKey", null));
        }
    }

    //imageLoader 加载图片
    public static void setImageByImageLoader(String uri, ImageView img) {
        DisplayImageOptions build = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageForEmptyUri(R.mipmap.b3h)
                .showImageOnFail(R.mipmap.b3h)
                .showImageOnLoading(R.mipmap.loading)
                .displayer(new CircleBitmapDisplayer())
                .build();
        ImageLoader.getInstance().displayImage(uri, img, build);
    }

    //注销EventBus
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }
}
