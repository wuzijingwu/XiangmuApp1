package com.smile.taobaodemo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.smile.taobaodemo.bean.Okhttp;
import com.smile.taobaodemo.bean.TuiJian;
import com.smile.taobaodemo.ui.activity.Jingxuanxiangqing;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.Request;

/**
 * Created by dell on 2017/10/8.
 */

public class Fragment1 extends Fragment {

    private View view;
    private ImageLoader imageLoader;
    private SwipeRefreshLayout swip;
    private RecyclerView recyclerview;
    private LinearLayoutManager linearLayoutManager;
    private String path = "http://c.3g.163.com/recommend/getChanListNews?channel=T1456112189138&size=20&devId=1uuFYbybIU2oqSRGyFrjCw%3D%3D&lat=%2F%2FOm%2B%2F8ScD%2B9fX1D8bxYWg%3D%3D&lon=LY2l8sFCNzaGzqWEPPgmUw%3D%3D&version=9.0&net=wifi&ts=1464769308&sign=bOVsnQQ6gJamli6%2BfINh6fC%2Fi9ydsM5XXPKOGRto5G948ErR02zJ6%2FKXOnxX046I&encryption=1&canal=meizu_store2014_news&mac=sSduRYcChdp%2BBL1a9Xa%2F9TC0ruPUyXM4Jwce4E9oM30%3D";
    private int p = 0;
    private String path2 = "&passport=";
    private Fragment1.mybasadpater mybasadpater;
    private List<TuiJian.美女Bean> beanList;
    private  int position;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment1, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        swip = view.findViewById(R.id.swip);
        recyclerview = view.findViewById(R.id.recyclerview);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(linearLayoutManager);
        getDates();





        swip.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                p++;
                getDates();
                mybasadpater.notifyDataSetChanged();
                swip.setRefreshing(false);
            }
        });
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition == beanList.size() - 1) {
                    p++;
                    getDates();
                    mybasadpater.notifyDataSetChanged();
                }
            }
        });
    }

    public void getDates() {
        Okhttp.getAsync(path + path2 + p, new Okhttp.DataCallBack() {


            @Override
            public void requestFailure(Request request, IOException e) {
            }
            @Override
            public void requestSuccess(String result) throws Exception {
                Gson gson = new Gson();
                TuiJian tuiJian = gson.fromJson(result, TuiJian.class);
                beanList = tuiJian.get美女();
                mybasadpater = new mybasadpater(beanList);
                recyclerview.setAdapter(mybasadpater);
            }
        });


    }

    class mybasadpater extends RecyclerView.Adapter {
        List<TuiJian.美女Bean> beanList;
        private myviewHolder myviewholder;
        private final DisplayImageOptions options;

        public mybasadpater(List<TuiJian.美女Bean> beanList) {
            this.beanList = beanList;
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
            View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.fragment1_item, parent, false);
            return new myviewHolder(view1);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            myviewholder = (myviewHolder) holder;
            myviewholder.text1.setText(beanList.get(position).getDocid());
            myviewholder.text0.setText(beanList.get(position).getReplyCount() + "");
            getimage(beanList.get(position).getImg(), myviewholder.image);
            myviewholder.text2.setText(beanList.get(position).getTitle());
            getimage(beanList.get(position).getImgsrc(), myviewholder.image2);
            myviewholder.image01.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mybasadpater.getItemId(position);
                    mybasadpater.notifyItemRemoved(position);
                }
            });

            myviewholder.text2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), Jingxuanxiangqing.class);
                    intent.putExtra("name1", beanList.get(position).getImgsrc());
                    intent.putExtra("name2", beanList.get(position).getTitle());
                    startActivity(intent);
                }
            });



            myviewholder.image2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), Jingxuanxiangqing.class);
                    intent.putExtra("name1", beanList.get(position).getImgsrc());
                    intent.putExtra("name2", beanList.get(position).getTitle());
                    startActivity(intent);
                }
            });




        }

        @Override
        public int getItemCount() {
            return beanList.size();
        }




        public class myviewHolder extends RecyclerView.ViewHolder {

            private ImageView image;
            private TextView text1;
            private TextView text0;
            private ImageView image2;
            private ImageView image01;
            private TextView text2;

            public myviewHolder(View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.image);
                image01 = itemView.findViewById(R.id.image01);
                text1 = itemView.findViewById(R.id.text1);
                text0 = itemView.findViewById(R.id.text0);
                image2 = itemView.findViewById(R.id.image2);
                text2 = itemView.findViewById(R.id.text2);
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
