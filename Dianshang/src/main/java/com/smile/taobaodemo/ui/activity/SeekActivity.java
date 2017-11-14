package com.smile.taobaodemo.ui.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.smile.taobaodemo.R;
import com.smile.taobaodemo.bean.SeekActivity_Bean;
import com.smile.taobaodemo.ui.adapter.Activity_Seek_Adapter;
import com.smile.taobaodemo.dao.MyApplication;
import com.smile.taobaodemo.view.SeekActivity_View;
import com.smile.taobaodemo.presenter.SeekActivity_presenter;

import java.util.List;

/**
 * 搜索结果页面
 */
public class SeekActivity extends AppCompatActivity implements View.OnClickListener, SeekActivity_View {

    private RecyclerView seek_recyclerView;
    private SeekActivity_presenter presenter;

    private String uriPath = "http://" + MyApplication.getMyIP() + "/mobile/index.php?act=goods&op=goods_list&page=100&gc_id=587";
    private SwipeRefreshLayout refreshlayout;
    private boolean boo;
    private Activity_Seek_Adapter adapter;
    private CheckBox seekCK;
    private ImageView toTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seek);
        InitView();
        //请求数据
        getData(uriPath);

        //设置刷新时旋转的圆圈的颜色
        refreshlayout.setColorSchemeColors(Color.RED, Color.YELLOW, Color.BLUE, Color.GRAY, Color.GREEN);
        //设置支持上啦刷新还是下拉加载,还是全都支持
//        refreshlayout.setDirection(SwipyRefreshLayoutDirection.BOTH);
        refreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                boo = true;
                getData(uriPath);
                refreshlayout.setRefreshing(false);
            }




        });


    }

    private void getData(String uri) {
        presenter.getData(uri);
    }

    private void InitView() {
        ImageView goback = (ImageView) findViewById(R.id.goback);
        //切换布局
        seekCK = (CheckBox) findViewById(R.id.seek_checkbox);
        seek_recyclerView = (RecyclerView) findViewById(R.id.seek_recyclerView);
        seek_recyclerView.setLayoutManager(new GridLayoutManager(SeekActivity.this, 2));
        //刷新工具
        refreshlayout = (SwipeRefreshLayout) findViewById(R.id.swipyRefreshLayout);
        //返回顶部的一个按钮,初始为影藏不可见
        toTop = (ImageView) findViewById(R.id.toTop);
        presenter = new SeekActivity_presenter(this);
        goback.setOnClickListener(this);
        seekCK.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.goback://返回按钮
                finish();
                break;
            case R.id.seek_checkbox://默认是GrdiView显示效果,选中的话变成listview的显示效果
                if (seekCK.isChecked()) {
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                    seek_recyclerView.setLayoutManager(linearLayoutManager);
                } else {
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
                    seek_recyclerView.setLayoutManager(gridLayoutManager);
                }
                //记得重新设置一次适配器,不然刷新不过来
                seek_recyclerView.setAdapter(adapter);
                break;
        }
    }

    @Override
    public void getResultSucced(final String result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                SeekActivity_Bean bean = gson.fromJson(result, SeekActivity_Bean.class);
                List<SeekActivity_Bean.DatasBean.GoodsListBean> goods_list = bean.getDatas().getGoods_list();
                if (adapter == null) {
                    adapter = new Activity_Seek_Adapter(goods_list, SeekActivity.this);
                    seek_recyclerView.setAdapter(adapter);
//                    seek_recyclerView.addItemDecoration(new SpaceItemDecoration(5));
                } else {
                    adapter.addData(goods_list, boo);
                    adapter.notifyDataSetChanged();
                }
                //滑动监听,当条目滑动一定程度的时候让返回顶部的按钮显示
                seek_recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        //首先得到页面布局
                        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                        //判断布局,如果是GridLayoutManager 布局,强转以后得到第一个可见的下标,大于5就
                        //  让返回顶部的按钮显示出来,点击这个按钮返回下标为0的item
                        if (layoutManager instanceof GridLayoutManager) {
                            int position = ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition();
                            if (position > 5) {
                                toTop.setVisibility(View.VISIBLE);
                                toTop.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        seek_recyclerView.scrollToPosition(0);
                                        //seek_recyclerView.smoothScrollToPosition(0);
                                    }
                                });
                            } else {
                                toTop.setVisibility(View.GONE);
                            }
                        } else if (layoutManager instanceof LinearLayoutManager) {
                            int position = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                            if (position > 10) {
                                toTop.setVisibility(View.VISIBLE);
                                toTop.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        seek_recyclerView.scrollToPosition(0);
                                        //seek_recyclerView.smoothScrollToPosition(0);
                                    }
                                });
                            } else {
                                toTop.setVisibility(View.GONE);
                            }
                        }
                    }
                });

            }
        });

    }

    @Override
    public Context context() {
        return this;
    }
}
