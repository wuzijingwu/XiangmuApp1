package com.smile.taobaodemo.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.smile.taobaodemo.R;
import com.smile.taobaodemo.bean.Mainhua;
import com.smile.taobaodemo.bean.Okhttp;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.Request;

/**
 * Created by dell on 2017/10/8.
 */

public class Jingxuanxiangqing extends Activity {

    private TextView text_it;
    private ImageView image_it;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private ListView listView;
    private String path = "http://api.kkmh.com/v1/daily/comic_lists/0?since=0&gender=0&sa_event=eyJwcm9qZWN0Ijoia3VhaWthbl9hcHAiLCJ0aW1lIjoxNDg3NzQyMjQwNjE1LCJwcm9wZXJ0aWVzIjp7IkhvbWVwYWdlVGFiTmFtZSI6IueDremXqCIsIlZDb21tdW5pdHlUYWJOYW1lIjoi54Ot6ZeoIiwiJG9zX3ZlcnNpb24iOiI0LjQuMiIsIkdlbmRlclR5cGUiOiLlpbPniYgiLCJGcm9tSG9tZXBhZ2VUYWJOYW1lIjoi54Ot6ZeoIiwiJGxpYl92ZXJzaW9uIjoiMS42LjEzIiwiJG5ldHdvcmtfdHlwZSI6IldJRkkiLCIkd2lmaSI6dHJ1ZSwiJG1hbnVmYWN0dXJlciI6ImJpZ25veCIsIkZyb21Ib21lcGFnZVVwZGF0ZURhdGUiOjAsIiRzY3JlZW5faGVpZ2h0IjoxMjgwLCJIb21lcGFnZVVwZGF0ZURhdGUiOjAsIlByb3BlcnR5RXZlbnQiOiJSZWFkSG9tZVBhZ2UiLCJGaW5kVGFiTmFtZSI6IuaOqOiNkCIsImFidGVzdF9ncm91cCI6MTEsIiRzY3JlZW5fd2lkdGgiOjcyMCwiJG9zIjoiQW5kcm9pZCIsIlRyaWdnZXJQYWdlIjoiSG9tZVBhZ2UiLCIkY2FycmllciI6IkNoaW5hIE1vYmlsZSIsIiRtb2RlbCI6IlZQaG9uZSIsIiRhcHBfdmVyc2lvbiI6IjMuNi4yIn0sInR5cGUiOiJ0cmFjayIsImRpc3RpbmN0X2lkIjoiQTo2YWRkYzdhZTQ1MjUwMzY1Iiwib3JpZ2luYWxfaWQiOiJBOjZhZGRjN2FlNDUyNTAzNjUiLCJldmVudCI6IlJlYWRIb21lUGFnZSJ9";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        imageLoader = ImageLoader.getInstance();
        File file = new File(Environment.getExternalStorageDirectory(), "Bwei");
        if (!file.exists())
            file.mkdirs();

        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(Jingxuanxiangqing.this)
                .diskCache(new UnlimitedDiskCache(file))
                .build();

        imageLoader.init(configuration);

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher)
                .cacheOnDisk(true)
                .build();
        text_it = findViewById(R.id.text_it);
        image_it = findViewById(R.id.image_it);
        Intent intent = getIntent();
        String name1 = intent.getStringExtra("name1");
        String name2 = intent.getStringExtra("name2");
        text_it.setText(name2);
        getimage(name1, image_it);
        listView = findViewById(R.id.listview);
        getDate();


    }

    public void getDate() {
        Okhttp.getAsync(path, new Okhttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                Gson gson = new Gson();
                Mainhua mainhua = gson.fromJson(result, Mainhua.class);
                List<Mainhua.DataBean.ComicsBean> comics = mainhua.getData().getComics();
                listView.setAdapter(new Mylistadapter(comics));


            }
        });
    }

    public class Mylistadapter extends BaseAdapter {
        List<Mainhua.DataBean.ComicsBean> comics;
        private TextView text;
        private ImageView image;

        public Mylistadapter(List<Mainhua.DataBean.ComicsBean> comics) {
            this.comics = comics;
        }

        @Override
        public int getCount() {


            return comics.size();
        }

        @Override
        public Object getItem(int position) {

            return comics.get(position);
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = convertView.inflate(Jingxuanxiangqing.this, R.layout.xiangqing_item, null);
                text = convertView.findViewById(R.id.text_xiangqing);
                image = convertView.findViewById(R.id.image_xiangqing);

            }
            text.setText(comics.get(position).getTopic().getDescription());
            getimage(comics.get(position).getTopic().getUser().getAvatar_url(), image);


            return convertView;
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
