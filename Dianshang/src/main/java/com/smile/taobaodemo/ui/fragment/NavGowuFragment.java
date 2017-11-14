package com.smile.taobaodemo.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.smile.taobaodemo.R;
import com.smile.taobaodemo.bean.SQliteBean;
import com.smile.taobaodemo.dao.GWC_dao;
import com.smile.taobaodemo.ui.adapter.FM_GWC_Adapter;

import java.util.ArrayList;


public class NavGowuFragment extends Fragment {

    private View view;
    private GWC_dao dao;
    private ExpandableListView EX_listview;
    private TextView notdata;
    private CheckBox checkAll;
    private TextView price;
    private TextView checked_shop;
    private FM_GWC_Adapter adapter;
    private TextView total_price;
    private static ArrayList<SQliteBean> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = View.inflate(getContext(), R.layout.fragment_gouwuche, null);
        return view;
    }
    public static NavGowuFragment newInstance() {
        NavGowuFragment fragment = new NavGowuFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        //当这个集合等于空的时候来查询数据
        list = dao.selectAll();
        //如果数据库无内容,显示TextView的提示无内容,recyclerView影藏
        if (list.size() > 0) {
            //如果出具库有数据就处理相关逻辑
            Listener(list);
        } else {
            //数据库没有东西的时候让TextView显示购物车为空
            notdata.setVisibility(View.VISIBLE);
        }
        //点击一级列表删除信息
        EX_listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //查询下标对应的商品id,在添加数据库的时候会专门来保存商品id
                String gc_id = list.get(position).getGc_id();
                //执行删除数据库
                dao.deleteData(gc_id);
                Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
                //删除成功以后.重新查询数据库
                list = dao.selectAll();
                //如果数据无内容,提示无内容,recyclerVIew影藏
                if (list.size() > 0) {
                    //如果出具库有数据就处理相关逻辑
                    Listener(list);
                } else {
                    //数据库没有东西的时候让TextView显示购物车为空
                    notdata.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });
    }

    //执行相关事件
    private void Listener(ArrayList<SQliteBean> list) {
        //有数据以后把这个textview隐藏
        notdata.setVisibility(View.GONE);
        //为二级列表设置适配器,list就是从数据库查询出来的数据
        adapter = new FM_GWC_Adapter(list, getContext());
        EX_listview.setAdapter(adapter);

        //全选按钮
        checkAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //代用适配器中的自定义方法,循环遍历二级列表,设置全选或全不选
                adapter.setCheckedAll();
                //得到查询到的选中项目的总条目数,和总价格
                String[] split = adapter.getShopNumber().split(",");
                //设置选中的商品个数
                checked_shop.setText("(" + split[1] + ")");
                //设置价格
                total_price.setText("合计:￥" + split[0]);
            }
        });
        //刷新选中的个数,和判断是否全部选中
        adapter.getNumberAndIsCheckAll(new FM_GWC_Adapter.NumberAndIsCheckAll() {
            @Override
            public void getNumber(View view, String shop, boolean ischecked) {
                //split[0]=价格, split[1]个数
                String[] split = shop.split(",");
                //设置选中的商品个数
                checked_shop.setText("(" + split[1] + ")");
                //设置价格
                total_price.setText("合计:￥" + split[0]);
                //设置商品全部选中的时候,全选按钮也自动选中
                checkAll.setChecked(ischecked);
            }
        });

        //设置当所有商品全部选中的时候,全选按钮也设置选中状态
        checkAll.setChecked(adapter.selectShopAll());
        //刷新适配器
        adapter.notifyDataSetChanged();
    }

    private void initView() {
        //显示数据
        EX_listview = (ExpandableListView) view.findViewById(R.id.gwc_ex_listview);
        //如果购物车空的时候让他显示购物车为空
        notdata = (TextView) view.findViewById(R.id.notdata);
        //操作数据库
        dao = new GWC_dao(getContext());
        //全选按钮
        checkAll = (CheckBox) view.findViewById(R.id.checkAll);
        //价格
        price = (TextView) view.findViewById(R.id.price);
        //结算的时候选中的店铺个数
        checked_shop = (TextView) view.findViewById(R.id.checked_shop);
        //总价格
        total_price = (TextView) view.findViewById(R.id.price);
        //定位按钮重启Activity,刷新数据
    }


}
