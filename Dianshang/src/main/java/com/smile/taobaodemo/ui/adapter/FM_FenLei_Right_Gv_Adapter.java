package com.smile.taobaodemo.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.smile.taobaodemo.R;
import com.smile.taobaodemo.bean.Fm_Fenlei_Right_gv_Bean;

import java.util.List;



public class FM_FenLei_Right_Gv_Adapter extends BaseAdapter {
    private Context context;
    private List<Fm_Fenlei_Right_gv_Bean.DatasBean.ClassListBean> list;

    public FM_FenLei_Right_Gv_Adapter(Context context, List<Fm_Fenlei_Right_gv_Bean.DatasBean.ClassListBean> list) {
        this.context = context;
        this.list = list;
    }



    @Override
    public int getCount() {
        return list.size() > 0 ? list.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return list != null ? list.get(position) : 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.fenlei_right_gridview_item, null);
            holder = new ViewHolder();
            holder.tv = (TextView) convertView.findViewById(R.id.gv_item_tv);
            holder.img = (ImageView) convertView.findViewById(R.id.gv_item_img);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv.setText(list.get(position).getGc_name());

        return convertView;
    }

    class ViewHolder {
        ImageView img;
        TextView tv;
    }
}
