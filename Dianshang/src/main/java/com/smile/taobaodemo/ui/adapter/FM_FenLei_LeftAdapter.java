package com.smile.taobaodemo.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.smile.taobaodemo.R;
import com.smile.taobaodemo.bean.Fm_FenLei_Left_Bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class FM_FenLei_LeftAdapter extends RecyclerView.Adapter<FM_FenLei_LeftAdapter.MyViewHodlder> {
    private List<Fm_FenLei_Left_Bean.DatasBean.ClassListBean> list;
    private Context context;
    private HashMap<Integer, Boolean> map;



    public FM_FenLei_LeftAdapter(List<Fm_FenLei_Left_Bean.DatasBean.ClassListBean> list, Context context) {
        this.list = list;
        this.context = context;
        map = new HashMap<>();
        //设置第一个默认选中
        for (int i = 0; i < list.size(); i++) {
            if (i == 0)
                map.put(i, true);
            else
                map.put(i, false);
        }

    }

    @Override
    public MyViewHodlder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fenlei_left_recycler_item, parent, false);
        return new MyViewHodlder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHodlder holder, final int position) {
        holder.left_item.setText(list.get(position).getGc_name());

        //设置布局点击事件
        holder.left_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置CheckBox单选,这样就可以只选中一个,然后改变选中状态
                SelectedItem(position);
                //点击调用自定义接口把下标传出去
                try {
                    monItemClickListener.ItemClick(v, list.get(position).getGc_id(), position);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        holder.left_item.setChecked(map.get(position));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void SelectedItem(int position) {
        Set<Map.Entry<Integer, Boolean>> entries = map.entrySet();
        for (Map.Entry<Integer, Boolean> entry : entries) {
            entry.setValue(false);
        }
        map.put(position, true);
        notifyDataSetChanged();
    }

    private OnItemClickListener monItemClickListener;

    public interface OnItemClickListener {
        void ItemClick(View view, String positionId, int position);
    }

    public void setOnItemClickListener(OnItemClickListener monItemClickListener) {
        this.monItemClickListener = monItemClickListener;
    }

    public class MyViewHodlder extends RecyclerView.ViewHolder {

        private CheckBox left_item;
        private View itemView;


        public MyViewHodlder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            left_item = (CheckBox) itemView.findViewById(R.id.left_item);
        }
    }
}
