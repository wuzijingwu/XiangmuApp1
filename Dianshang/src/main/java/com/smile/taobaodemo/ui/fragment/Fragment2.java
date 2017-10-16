package com.smile.taobaodemo.ui.fragment;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.smile.taobaodemo.R;
import com.smile.taobaodemo.bean.Shangcheng;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.Request;

/**
 * Created by dell on 2017/10/8.
 */

public class Fragment2 extends Fragment {
    private View view1;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageLoader imageLoader;
   List<Shangcheng.GoodsListBean> goods_list;
    private String path = "http://apiv3.yangkeduo.com/v5/newlist?&size=20";
    private int page=1;
    private GridLayoutManager gridLayoutManager;
    private mydaspter mydaspter;
    private  int count = 0;
    private ImageView righticon;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view1 = inflater.inflate(R.layout.fragment2, container, false);
        return view1;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        swipeRefreshLayout = view1.findViewById(R.id.swwpe);
        recyclerView = view1.findViewById(R.id.recy);
        righticon = view1.findViewById(R.id.r);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);



        righticon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (count++){
                    case 0:recyclerView.setLayoutManager(gridLayoutManager);
                        break;
                    case 1:recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        count=0;
                        break;
                }
            }
        });



        getDates();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page++;
                getDates();
                mydaspter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastVisibleItemPosition = gridLayoutManager.findLastVisibleItemPosition();
                if(lastVisibleItemPosition==goods_list.size()-1){
                    page++;
                    getDates();
                    mydaspter.notifyDataSetChanged();
                }
            }
        });





    }


    public void getDates() {
        Okhttp.getAsync(path+page, new Okhttp.DataCallBack() {



            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Gson gson = new Gson();
                Shangcheng shangcheng = gson.fromJson(result, Shangcheng.class);
                goods_list = shangcheng.getGoods_list();
                mydaspter = new mydaspter(goods_list);
                recyclerView.setAdapter(mydaspter);

            }
        });
    }

    public class mydaspter extends RecyclerView.Adapter {


        private MyViewHolder myviewholde;
        List<Shangcheng.GoodsListBean> goods_list;
        private final DisplayImageOptions options;

        public mydaspter(List<Shangcheng.GoodsListBean> goods_list) {
            this.goods_list = goods_list;
            imageLoader = ImageLoader.getInstance();
            File file = new File(Environment.getExternalStorageDirectory(), "Bwei");
            if (!file.exists())
                file.mkdirs();

            ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(getActivity())
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
            View inflate = LayoutInflater.from(getContext()).inflate(R.layout.fragemt2_item, parent, false);
            return new MyViewHolder(inflate);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            myviewholde = (MyViewHolder) holder;
            myviewholde.tezt.setText(goods_list.get(position).getGoods_name());
            getimage(goods_list.get(position).getImage_url(), myviewholde.imadfge);


        }

        @Override
        public int getItemCount() {
            return goods_list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            private final ImageView imadfge;
            private final TextView tezt;

            public MyViewHolder(View itemView) {
                super(itemView);
                imadfge = (ImageView) itemView.findViewById(R.id.imadfge);
                tezt = (TextView) itemView.findViewById(R.id.tezt);
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
