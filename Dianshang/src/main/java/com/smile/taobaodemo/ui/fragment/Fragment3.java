package com.smile.taobaodemo.ui.fragment;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.smile.taobaodemo.R;
import com.smile.taobaodemo.bean.Mews;
import com.smile.taobaodemo.bean.Okhttp;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.Request;



public class Fragment3 extends Fragment implements XRecyclerView.LoadingListener {

    private View view;
    private XRecyclerView xRecyclerView;
    private ImageLoader imageLoader;
//    private String path = "http://op.juhe.cn/onebox/movie/pmovie?key=b554809d105a765335136fae9f49a56b&city=%E5%8C%97%E4%BA%AC";

    private String path = "http://api.expoon.com/AppNews/getNewsList/type/1/p/";
    private int p = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment3, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        xRecyclerView = view.findViewById(R.id.xrecycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        xRecyclerView.setLayoutManager(linearLayoutManager);

        xRecyclerView.setLoadingListener(this);
        xRecyclerView.setPullRefreshEnabled(true);
        xRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallPulseRise);
        getDates();

    }

    @Override
    public void onRefresh() {
        p++;
        getDates();
        xRecyclerView.refreshComplete();
    }

    @Override
    public void onLoadMore() {
        p++;
        getDates();
        xRecyclerView.loadMoreComplete();
    }

    public void getDates() {
        Okhttp.getAsync(path + p, new Okhttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Gson gson = new Gson();
                Mews mews = gson.fromJson(result, Mews.class);
                List<Mews.DataBean> data = mews.getData();
                xRecyclerView.setAdapter(new Myfragmentdapter(data));

            }
        });
    }

    public class Myfragmentdapter extends RecyclerView.Adapter {
        List<Mews.DataBean> data;
        private myTViewHolder mytviewholder;
        private final DisplayImageOptions options;

        public Myfragmentdapter(List<Mews.DataBean> data) {
            this.data = data;
            imageLoader=ImageLoader.getInstance();
                  File file= new File(Environment.getExternalStorageDirectory(),"Bwei");
                  if(!file.exists())
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
            View view01 = LayoutInflater.from(getContext()).inflate(R.layout.fragment3_item, parent, false);
            return new myTViewHolder(view01);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            mytviewholder = (myTViewHolder) holder;
            mytviewholder.textfragment_item.setText(data.get(position).getNews_title());
            getimage(data.get(position).getPic_url(),mytviewholder.imge1);
            getimage(data.get(position).getPic_url(),mytviewholder.imge2);
            getimage(data.get(position).getPic_url(),mytviewholder.imge3);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        public class myTViewHolder extends RecyclerView.ViewHolder {


            private final TextView textfragment_item;
            private final ImageView imge1;
            private final ImageView imge2;
            private final ImageView imge3;

            public myTViewHolder(View itemView) {
                super(itemView);
                textfragment_item = itemView.findViewById(R.id.textfragment_item);
                imge1 = itemView.findViewById(R.id.imge1);
                imge2 = itemView.findViewById(R.id.imge2);
                imge3 = itemView.findViewById(R.id.imge3);


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
