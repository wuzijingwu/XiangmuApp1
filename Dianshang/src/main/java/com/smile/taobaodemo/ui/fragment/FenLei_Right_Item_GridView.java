package com.smile.taobaodemo.ui.fragment;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;



public class FenLei_Right_Item_GridView extends GridView {

    public FenLei_Right_Item_GridView(Context context) {
        super(context);
    }

    public FenLei_Right_Item_GridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FenLei_Right_Item_GridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public FenLei_Right_Item_GridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
