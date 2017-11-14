package com.smile.taobaodemo.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.smile.taobaodemo.R;
import com.smile.taobaodemo.dao.MyApplication;
import com.smile.taobaodemo.bean.Fenlei;
import com.smile.taobaodemo.bean.Fm_FenLei_Left_Bean;
import com.smile.taobaodemo.bean.Fm_FenLei_Right_Bean;
import com.smile.taobaodemo.presenter.Fm_FenLei_presenter;
import com.smile.taobaodemo.ui.adapter.FM_FenLei_LeftAdapter;
import com.smile.taobaodemo.ui.adapter.FM_Fenlei_RightAdapter;
import com.smile.taobaodemo.view.FM_FenLei_View;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import q.rorbin.verticaltablayout.VerticalTabLayout;


public class NavFenleiFragment extends Fragment implements FM_FenLei_View {

    private View inflate;
    private VerticalTabLayout verticaltabLayout;
    private ViewPager viewpages;
    int position;

    private View view;
    private final static int SCANNIN_GREQUEST_CODE = 1;
    private RecyclerView left_recyclerView;
    private Fm_FenLei_presenter fm_fenLei_presenter;
    private String LeftUrlpath = "http://" + MyApplication.getMyIP() + "/mobile/index.php?act=goods_class";//左侧分类接口
    private String RightUrlpath = "http://" + MyApplication.getMyIP() + "/mobile/index.php?act=goods_class&gc_id=1";//右侧子类接口
    private List<Fm_FenLei_Left_Bean.DatasBean.ClassListBean> left_list;
    private Banner right_vp;
    private RecyclerView right_gridview;
    private String LeftId;


    private List<Fenlei.DatasBean.ClassListBean> class_list;

    public static NavFenleiFragment newInstance() {
        NavFenleiFragment fragment = new NavFenleiFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_navigation_help, container, false);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        InItView();
        //请求左侧的数据
        getLeftData();
        //默认请求第一种分类的数据,下面在点击的时候再刷新更换数据
        getRightData(RightUrlpath);
        //设置适配器
        ArrayList<Integer> list = new ArrayList<>();
        list.add(R.mipmap.fenlei03);
        list.add(R.mipmap.fenlei04);
        list.add(R.mipmap.fenlei05);
        list.add(R.mipmap.fenlei06);
        right_vp.setImageLoader(new GlideImageLoader());
        right_vp.setImages(list);
        right_vp.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        right_vp.setDelayTime(3000);
        right_vp.start();
    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);
        }
    }


    //初始化View
    private void InItView() {
        left_recyclerView = (RecyclerView) inflate.findViewById(R.id.left_RecyclerView);
        right_vp = (Banner) inflate.findViewById(R.id.right_vp);//右侧顶部的图片
        right_gridview = (RecyclerView) inflate.findViewById(R.id.right_recyclerView);
        fm_fenLei_presenter = new Fm_FenLei_presenter(this);
    }


    @Override
    public Context context() {
        return getContext();
    }

    //左侧接口,请求成功的数据
    @Override
    public void onLeftResultSucced(final String result) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                Fm_FenLei_Left_Bean fm_fenLeiLeftBean = gson.fromJson(result, Fm_FenLei_Left_Bean.class);
                left_list = fm_fenLeiLeftBean.getDatas().getClass_list();
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                left_recyclerView.setLayoutManager(linearLayoutManager);
                //左侧适配器
                final FM_FenLei_LeftAdapter adapter = new FM_FenLei_LeftAdapter(left_list, getContext());
                left_recyclerView.setAdapter(adapter);

                //自定义点击事件,得到左侧分类的下标id,然后传给右侧的recyclerView使用
                adapter.setOnItemClickListener(new FM_FenLei_LeftAdapter.OnItemClickListener() {
                    private int childCount = 0;
                    private int middlechild = 0;

                    @Override
                    public void ItemClick(View view, String positionId, int position) {
                        LeftId = positionId;
                        //请求右侧数据
                        getRightData("http://" + MyApplication.getMyIP()
                                + "/mobile/index.php?act=goods_class&gc_id=" + LeftId);
                        //得到布局
                        RecyclerView.LayoutManager manager = left_recyclerView.getLayoutManager();
                        //竖排类型,所以强转LinearLayoutManager,如果是ListView就不需要强转
                        LinearLayoutManager layoutManager = (LinearLayoutManager) manager;

                        //得到屏幕可见的item的总数
                        childCount = layoutManager.getChildCount();
                        if (childCount != left_list.size()) {
                            //可见item的总数除以2  就可以拿到中间位置
                            middlechild = childCount / 2;
                        }

                        //判断你点的是中间位置的上面还是中间的下面位置
                        //RecyclerView必须加 && position != 2,listview不需要
                        if (position <= (layoutManager.findFirstVisibleItemPosition() + middlechild) && position != 2) {
                            left_recyclerView.smoothScrollToPosition(position + 1 - middlechild);
                        } else {
                            left_recyclerView.smoothScrollToPosition(position - 1 + middlechild);
                        }
                    }
                });
            }
        });
    }

    //右侧RecyclerView请求数据成功返回
    @Override
    public void onRightResultSucced(final String result) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                Fm_FenLei_Right_Bean bean = gson.fromJson(result, Fm_FenLei_Right_Bean.class);
                List<Fm_FenLei_Right_Bean.DatasBean.ClassListBean> listBeen = bean.getDatas().getClass_list();
                FM_Fenlei_RightAdapter rightAdapter = new FM_Fenlei_RightAdapter(getContext(), listBeen, getActivity());
                right_gridview.setLayoutManager(new LinearLayoutManager(getContext()));
                right_gridview.setAdapter(rightAdapter);
                rightAdapter.notifyDataSetChanged();
            }
        });
    }

    //请求右侧数据
    public void getRightData(String url) {
        fm_fenLei_presenter.getRightData(url);
    }

    //请求左侧数据
    private void getLeftData() {
        fm_fenLei_presenter.getLeftData(LeftUrlpath);
    }

}

