package com.smile.taobaodemo.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smile.taobaodemo.R;

import java.util.ArrayList;



public class NavWeitaoFragment extends Fragment {

    private View view;
    private TextView jingxuan;
    private TextView shiping;
    private TextView gushi;
    private ViewPager viewpager;
    private ArrayList<Fragment> list;
    private Fragment1 fragment1;
    private Fragment2 fragment2;
    private Fragment3 fragment3;

    public static NavWeitaoFragment newInstance() {
        NavWeitaoFragment fragment = new NavWeitaoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_navigation_we, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        jingxuan = view.findViewById(R.id.jingxuan);
        shiping = view.findViewById(R.id.shiping);
        gushi = view.findViewById(R.id.gushi);
        viewpager = view.findViewById(R.id.viewpager);

        list = new ArrayList<>();
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();
//        fragment4 = new Fragment4();
        list.add(fragment1);
        list.add(fragment2);
        list.add(fragment3);

//        FragmentStatePagerAdapter
        viewpager.setAdapter(new FragmentStatePagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });

        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        jingxuan.setTextColor(Color.RED);
                        shiping.setTextColor(Color.BLACK);
                        gushi.setTextColor(Color.BLACK);

                        break;
                    case 1:
                        jingxuan.setTextColor(Color.BLACK);
                        shiping.setTextColor(Color.RED);
                        gushi.setTextColor(Color.BLACK);
                        break;
                    case 2:
                        gushi.setTextColor(Color.RED);
                        shiping.setTextColor(Color.BLACK);
                        jingxuan.setTextColor(Color.BLACK);
                        break;




                }



            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        jingxuan.setTextColor(Color.RED);
        jingxuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewpager.setCurrentItem(0);
                jingxuan.setTextColor(Color.RED);
                shiping.setTextColor(Color.BLACK);
                gushi.setTextColor(Color.BLACK);



            }
        });
        shiping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewpager.setCurrentItem(1);
                shiping.setTextColor(Color.RED);
                jingxuan.setTextColor(Color.BLACK);
                gushi.setTextColor(Color.BLACK);



            }
        });
        gushi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewpager.setCurrentItem(2);
                shiping.setTextColor(Color.BLACK);
                jingxuan.setTextColor(Color.BLACK);
                gushi.setTextColor(Color.RED);


            }
        });



    }


}
