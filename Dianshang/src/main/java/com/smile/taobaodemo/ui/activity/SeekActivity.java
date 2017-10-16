package com.smile.taobaodemo.ui.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.smile.taobaodemo.R;
import com.smile.taobaodemo.bean.Shoubiao;
import com.smile.taobaodemo.ui.fragment.MyApplication;
import com.smile.taobaodemo.ui.fragment.Okhttp;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.Request;

/**
 * Created by dell on 2017/10/13.
 */

public class SeekActivity extends Activity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private String path = "http://169.254.105.18/mobile/index.php?act=goods&op=goods_list&page=100&gc_id=587";
    private boolean boo;
    private ImageLoader imageLoader;
    //    &page=100

    private Myrecycadapter myrecycadapter;
    List<Shoubiao.DatasBean.GoodsListBean> goods_list;
    private CheckBox seekcheckbox;
    private int count = 0;
    private LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seek);
        swipeRefreshLayout = findViewById(R.id.swipyRefreshLayout);
        recyclerView = findViewById(R.id.seek_recyclerView);
        seekcheckbox = findViewById(R.id.seek_checkbox);

        gridLayoutManager = new GridLayoutManager(this, 2);
        linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(gridLayoutManager);
        getDates();

        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.YELLOW, Color.BLUE, Color.GRAY, Color.GREEN);
//        swipeRefreshLayout.setDirection(SwipyRefreshLayoutDirection.BOTH);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                boo = true;
                getDates();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                boo = true;
                getDates();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        seekcheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (count++) {
                    case 0:
                        recyclerView.setLayoutManager(gridLayoutManager);
                        break;
                    case 1:
                        recyclerView.setLayoutManager(linearLayoutManager);
                        count = 0;
                        break;
                }
            }
        });
    }

    public void getDates() {
        Okhttp.getAsync(path, new Okhttp.DataCallBack() {

            private Myrecycadapter myrecycadapter;

            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Gson gson = new Gson();
                Shoubiao shoubiao = gson.fromJson(result, Shoubiao.class);
                List<Shoubiao.DatasBean.GoodsListBean> goods_list = shoubiao.getDatas().getGoods_list();
                myrecycadapter = new Myrecycadapter(goods_list);
                recyclerView.setAdapter(myrecycadapter);
            }
        });
    }

    class Myrecycadapter extends RecyclerView.Adapter {
        List<Shoubiao.DatasBean.GoodsListBean> goods_list;

        private myViewHolder myviewholder;
        private final DisplayImageOptions options;

        public Myrecycadapter(List<Shoubiao.DatasBean.GoodsListBean> goods_list) {
            this.goods_list = goods_list;
            imageLoader = ImageLoader.getInstance();
            File file = new File(Environment.getExternalStorageDirectory(), "Bwei");
            if (!file.exists())
                file.mkdirs();
            ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(SeekActivity.this)
                    .diskCache(new UnlimitedDiskCache(file))
                    .build();
            imageLoader.init(configuration);
            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.mipmap.ic_launcher)
                    .cacheOnDisk(true)
                    .build();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(SeekActivity.this).inflate(R.layout.seekactivity_layout, parent, false);
            return new myViewHolder(inflate);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            myviewholder = (myViewHolder) holder;
            myviewholder.textview.setText(goods_list.get(position).getGoods_name());
//            myviewholder.imageview.disp
            String path = goods_list.get(position).getGoods_image_url();
            String replace = path.replace("127.0.0.1", MyApplication.getMyIP());
            //加载图片
            Glide.with(SeekActivity.this).load(replace)
                    .error(R.mipmap.ic_empty)
                    .placeholder(R.mipmap.loading)
                    .into(myviewholder.imageview);
//            getimage(replace, myviewholder.imageview);
        }

        @Override
        public int getItemCount() {
            return goods_list.size();
        }

        public class myViewHolder extends RecyclerView.ViewHolder {


            private final ImageView imageview;
            private final TextView textview;

            public myViewHolder(View itemView) {
                super(itemView);
                imageview = itemView.findViewById(R.id.image_shoubaio);
                textview = itemView.findViewById(R.id.text_xianging);
            }
        }


    }

    public void getimage(String path, ImageView imageView) {

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheOnDisk(true)
                .cacheInMemory(true)
                .build();
        ImageLoader.getInstance().displayImage(path, imageView, options);


    }


}
