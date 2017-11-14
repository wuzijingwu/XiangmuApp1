package com.smile.taobaodemo.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.smile.taobaodemo.R;
import com.smile.taobaodemo.bean.Fm_FenLei_Right_Bean;
import com.smile.taobaodemo.bean.Fm_Fenlei_Right_gv_Bean;
import com.smile.taobaodemo.ui.activity.SeekActivity;
import com.smile.taobaodemo.dao.MyApplication;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;





public class FM_Fenlei_RightAdapter extends RecyclerView.Adapter<FM_Fenlei_RightAdapter.MyViewHolder> {
    private Context context;
    private Activity activity;
    private List<Fm_FenLei_Right_Bean.DatasBean.ClassListBean> list;

    public FM_Fenlei_RightAdapter(Context context, List<Fm_FenLei_Right_Bean.DatasBean.ClassListBean> list
            , Activity activity) {
        this.context = context;
        this.list = list;
        this.activity = activity;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.fenlei_right_recycler_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.item_name.setText(list.get(position).getGc_name());
        //然后设置下方的GridView的适配器
        String url = "http://" + MyApplication.getMyIP() + "/mobile/index.php?act=goods_class&gc_id=" + list.get(position).getGc_id();
        setGridView(holder, url);

        //点击GridView的item跳转搜索界面
        holder.gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, SeekActivity.class);
                context.startActivity(intent);
            }
        });
    }

    //设置GridView的数据
    private void setGridView(final MyViewHolder holder, String url) {
        //调用OkHttpClient请求数据
        OkHttpClient okHttpClient = MyApplication.okHttpClient();
        Request build = new Request.Builder().url(url).build();
        okHttpClient.newCall(build).enqueue(new Callback() {

            private String result;

            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        result = response.body().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Gson gson = new Gson();
                            Fm_Fenlei_Right_gv_Bean gv_bean = gson.fromJson(result, Fm_Fenlei_Right_gv_Bean.class);
                            List<Fm_Fenlei_Right_gv_Bean.DatasBean.ClassListBean> listBean = gv_bean.getDatas().getClass_list();
                            FM_FenLei_Right_Gv_Adapter adapter = new FM_FenLei_Right_Gv_Adapter(context, listBean);
                           holder.gridview.setAdapter(adapter);
                        }
                    });
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView item_name;
        private GridView gridview;

        public MyViewHolder(View itemView) {
            super(itemView);
            item_name = (TextView) itemView.findViewById(R.id.right_item_name);
            gridview = (GridView) itemView.findViewById(R.id.gridview);
        }
    }
}
